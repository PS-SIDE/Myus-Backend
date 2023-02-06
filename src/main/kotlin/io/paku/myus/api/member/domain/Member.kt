package io.paku.myus.api.member.domain

import io.paku.myus.api.member.dto.request.MemberDto
import io.paku.myus.base.TimeStamped
import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(
    email: String,
    password: String
): TimeStamped() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, unique = true)
    var email: String = email
        protected set

    @Column(nullable = false)
    var password: String = password
        protected set

    fun updateMember(memberDto: MemberDto) {
        this.email = memberDto.email
        this.password = memberDto.password
    }

    fun updateEmail(email: String) {
        this.email = email
    }

    fun updatePassword(password: String) {
        this.password = password
    }
}