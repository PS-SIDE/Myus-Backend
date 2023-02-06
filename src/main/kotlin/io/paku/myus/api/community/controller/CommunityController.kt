package io.paku.myus.api.community.controller

import io.paku.myus.api.community.dto.request.CommunityRequestDto
import io.paku.myus.api.community.dto.response.CommunityResponseDto
import io.paku.myus.api.community.service.CommunityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/community")
class CommunityController(
    private val communityService: CommunityService
) {

//    @Operation(summary = "CREATE COMMUNITY", description = "COMMUNITY API")
//    @ApiResponses(
//        ApiResponse(
//            responseCode = "200",
//            content = [Content(schema = Schema(implementation = PostCommunityUseCase::class))]
//        ),
//        ApiResponse(responseCode = "404", description = "not_found", content = [Content()])
//    )
    @PostMapping
    fun createCommunity(
        @RequestBody
        requestBody: CommunityRequestDto
    ) {
        ResponseEntity
            .ok()
            .body(communityService.postCommunity(requestBody))
    }

//    @Operation(summary = "GET COMMUNITY", description = "COMMUNITY API")
//    @ApiResponses(
//        ApiResponse(
//            responseCode = "200",
//            content = [Content(schema = Schema(implementation = GetCommunityUseCase::class))]
//        ),
//        ApiResponse(responseCode = "404", description = "not_found", content = [Content()])
//    )
    @GetMapping
    fun getCommunity(
        @RequestParam
        start: Int,
        @RequestParam
        perPage: Int
    ): ResponseEntity<List<CommunityResponseDto>> {
        return ResponseEntity
            .ok()
            .body(communityService.getCommunity(perPage))
    }
}