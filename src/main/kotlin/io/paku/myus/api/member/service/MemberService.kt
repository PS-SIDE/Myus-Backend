package io.paku.myus.api.member.service

import io.paku.myus.api.member.dto.request.MemberDto
import io.paku.myus.api.member.dto.response.MemberResponseDto
import io.paku.myus.api.member.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    @Transactional(readOnly = true)
    fun getAllMembers(): List<MemberResponseDto> {
        return memberRepository.findAll().stream().map { member ->
            MemberResponseDto(member.id, member.email)
        }.collect(Collectors.toList())
    }

    @Transactional(readOnly = true)
    fun getMember(memberId: Long): MemberResponseDto {
        val findMember = memberRepository.findByIdOrNull(memberId) ?: throw IllegalArgumentException("존재하지 않는 ID 입니다.")

        return MemberResponseDto(findMember.id, findMember.email)
    }

    @Transactional
    fun updateMember(memberId: Long?, memberDto: MemberDto) {
        val findMember = memberRepository.findByIdOrNull(memberId) ?: throw IllegalArgumentException("존재하지 않는 ID 입니다.")
        findMember.updateMember(memberDto)
    }

    @Transactional
    fun deleteMember(memberId: Long) {
        memberRepository.deleteById(memberId)
    }
}