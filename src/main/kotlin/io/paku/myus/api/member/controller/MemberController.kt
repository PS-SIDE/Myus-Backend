package io.paku.myus.api.member.controller

import io.paku.myus.api.member.dto.request.MemberDto
import io.paku.myus.api.member.dto.response.MemberResponseDto
import io.paku.myus.api.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/{memberId}")
    fun getMember(
        @PathVariable memberId: Long
    ): ResponseEntity<MemberResponseDto> {
        return ResponseEntity
            .ok()
            .body(memberService.getMember(memberId))
    }

    @PutMapping("/{memberId}")
    fun putMember(
        @PathVariable memberId: Long,
        @RequestBody memberDto: MemberDto
    ) {
        ResponseEntity
            .ok()
            .body(memberService.updateMember(memberId, memberDto))
    }

    @DeleteMapping("/{memberId}")
    fun deleteMember(
        @PathVariable memberId: Long
    ) {
        ResponseEntity
            .ok()
            .body(memberService.deleteMember(memberId))
    }
}