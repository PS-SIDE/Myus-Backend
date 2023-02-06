package io.paku.myus.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import io.paku.myus.api.auth.dto.response.AuthDto
import io.paku.myus.security.UserDetailsServiceImpl
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtUtils(
    private val userDetailsService: UserDetailsServiceImpl
) {
    private val secretKey: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateAuthDto(email: String): AuthDto {
        return AuthDto(
            accessToken = generateAccessToken(email),
            refreshToken = generateRefreshToken(email)
        )
    }

    fun generateAuthDto(oAuth2User: OAuth2User): AuthDto {
        return AuthDto(
            accessToken = generateAccessToken(oAuth2User),
            refreshToken = generateRefreshToken(oAuth2User)
        )
    }

    fun generateAccessToken(email: String): String {
        return generateToken(email, ACCESS_TOKEN_EXP_TIME)
    }

    fun generateAccessToken(oauth2User: OAuth2User): String {
        return generateToken(oauth2User, ACCESS_TOKEN_EXP_TIME)
    }

    fun generateRefreshToken(email: String): String {
        return generateToken(email, REFRESH_TOKEN_EXP_TIME)
    }

    fun generateRefreshToken(oauth2User: OAuth2User): String {
        return generateToken(oauth2User, REFRESH_TOKEN_EXP_TIME)
    }

    fun validateToken(token: String): Boolean {
        val claims = getAllClaims(token)
        val exp = claims.expiration
        return exp.after(Date())
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val email = getEmail(token)
        return (email == userDetails.username && validateToken(token))
    }

    fun getEmail(token: String): String {
        val claims = getAllClaims(token)
        return claims["email"] as String
    }

    fun getAuthentication(email: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun generateToken(email: String, expTime: Long): String {
        val claims = Jwts.claims()
        claims["email"] = email

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expTime))
            .signWith(secretKey)
            .compact()
    }

    private fun generateToken(oAuth2User: OAuth2User, expTime: Long): String {
        return Jwts.builder()
            .setSubject(oAuth2User.attributes["email"] as String)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expTime))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    companion object {
        const val ACCESS_TOKEN_EXP_TIME: Long = 1000L * 60 * 3
        const val REFRESH_TOKEN_EXP_TIME: Long = 1000L * 60 * 24 * 3
    }
}