package io.paku.myus.api.auth.service

import io.paku.myus.api.auth.dto.request.AuthRequestDto
import io.paku.myus.api.auth.dto.response.AuthDto
import io.paku.myus.api.member.domain.Member
import io.paku.myus.api.member.repository.MemberRepository
import io.paku.myus.utils.JwtUtils
import io.paku.myus.utils.RedisUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils,
    private val redisUtils: RedisUtils,
) {

    @Transactional
    fun register(authRequestDto: AuthRequestDto): AuthDto {
        memberRepository.save(
            Member(
                email = authRequestDto.email,
                password = passwordEncoder.encode(authRequestDto.password)
            )
        )

        return login(authRequestDto)
    }

    @Transactional(readOnly = true)
    fun login(authRequestDto: AuthRequestDto): AuthDto {
        val authentication = UsernamePasswordAuthenticationToken(authRequestDto.email, authRequestDto.password)
        try {
            authenticationManager.authenticate(authentication)
        } catch (e: BadCredentialsException) {
            throw BadCredentialsException("로그인 실패")
        }

        return createAndCacheAuth(authentication)
    }

    @Transactional
    fun socialLogin(oAuth2User: OAuth2User): AuthDto {
        val email = oAuth2User.attributes["email"] as String
        if (!memberRepository.existsByEmail(email)) {
            val member = Member(email = email, password = "string")
            memberRepository.save(member)
        }

        val authDto = jwtUtils.generateAuthDto(oAuth2User)
        redisUtils.setDataWithExpiration(
            "RefreshToken:${jwtUtils.getEmail(authDto.accessToken)}",
            authDto.refreshToken,
            JwtUtils.REFRESH_TOKEN_EXP_TIME
        )
        return authDto
    }

    @Transactional
    fun refreshAuth(authDto: AuthDto): String {
        if (!jwtUtils.validateToken(authDto.refreshToken)) {
            throw BadCredentialsException("RefreshToken 이 유효하지 않습니다")
        }

        val userName = jwtUtils.getEmail(authDto.accessToken)
        val authentication = jwtUtils.getAuthentication(userName)
        val refreshToken = redisUtils.getData("RefreshToken:${authentication.name}")

        if (refreshToken != authDto.refreshToken) {
            throw BadCredentialsException("토큰의 유저정보가 일치하지않습니다.")
        }

        return createAndCacheAuth(authentication).accessToken
    }

    private fun createAndCacheAuth(authentication: Authentication): AuthDto {
        val authDto = jwtUtils.generateAuthDto(authentication.name)
        redisUtils.setDataWithExpiration(
            "RefreshToken:${authentication.name}",
            authDto.refreshToken,
            JwtUtils.REFRESH_TOKEN_EXP_TIME
        )
        return authDto
    }
}