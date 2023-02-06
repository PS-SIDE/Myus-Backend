package io.paku.myus.api.member.repository

import io.paku.myus.api.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?

    fun existsByEmail(email: String): Boolean
}