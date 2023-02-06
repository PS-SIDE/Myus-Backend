package io.paku.myus.api.member.dto.request

import io.paku.myus.api.member.domain.Member

data class MemberDto(
    var email: String,
    var password: String
) {
    fun toEntity(): Member {
        return Member(this.email, this.password)
    }
}