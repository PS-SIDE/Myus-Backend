package io.paku.myus.api.community.dto.response

import java.time.LocalDateTime

data class CommunityResponseDto(
    val id: Long?,
    val category: String,
    val title: String,
    val content: String,
    val images: List<String>?,
    val viewCount: Int,
    val commentCount: Int,
    val isBookmarked: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)