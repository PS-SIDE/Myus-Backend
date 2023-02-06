package io.paku.myus.api.community.domain.mapper

import io.paku.myus.api.community.domain.Community
import io.paku.myus.api.community.dto.request.CommunityRequestDto
import io.paku.myus.base.Mapper

object CommunityEntityMapper: Mapper<CommunityRequestDto, Community> {
    override fun mapToData(from: CommunityRequestDto): Community {
        return Community(
            category = from.category,
            title = from.title,
            content = from.content,
            images = from.images
        )
    }
}