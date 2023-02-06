package io.paku.myus.security

import io.paku.myus.api.member.domain.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(
    val member: Member
): UserDetails {
    private var enabled: Boolean = true

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return AuthorityUtils.createAuthorityList()
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.email
    }

    override fun isAccountNonExpired(): Boolean {
        return enabled
    }

    override fun isAccountNonLocked(): Boolean {
       return enabled
    }

    override fun isCredentialsNonExpired(): Boolean {
       return enabled
    }

    override fun isEnabled(): Boolean {
       return enabled
    }
}