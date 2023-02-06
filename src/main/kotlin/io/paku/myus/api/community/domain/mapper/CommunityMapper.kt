package io.paku.myus.api.community.domain.mapper

import io.paku.myus.api.community.domain.Community
import io.paku.myus.api.community.dto.response.CommunityResponseDto
import io.paku.myus.base.Mapper

object CommunityMapper: Mapper<Community, CommunityResponseDto> {
    override fun mapToData(from: Community): CommunityResponseDto {
        return CommunityResponseDto(
            id = from.id,
            category = from.category,
            title = from.title,
            content = from.content,
            images = from.images,
            viewCount = from.viewCount,
            commentCount = from.commentCount,
            isBookmarked = from.isBookmarked,
            createdAt = from.createdAt,
            updatedAt = from.updatedAt
        )
    }
}