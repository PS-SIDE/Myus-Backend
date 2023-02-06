package io.paku.myus.security

import io.paku.myus.api.member.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val memberRepository: MemberRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val member = memberRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("존제하지않는 회원 입니다")

        return UserDetailsImpl(member)

    }
}