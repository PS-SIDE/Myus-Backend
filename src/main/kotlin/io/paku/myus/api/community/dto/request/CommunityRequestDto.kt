package io.paku.myus.api.community.dto.request

data class CommunityRequestDto(
    var category: String,
    var title: String,
    var content: String,
    var images: List<String>?
)