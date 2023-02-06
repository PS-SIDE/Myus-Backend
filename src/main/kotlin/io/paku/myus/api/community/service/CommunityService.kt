package io.paku.myus.api.community.service

import io.paku.myus.api.community.domain.mapper.CommunityEntityMapper
import io.paku.myus.api.community.domain.mapper.CommunityMapper
import io.paku.myus.api.community.dto.request.CommunityRequestDto
import io.paku.myus.api.community.dto.response.CommunityResponseDto
import io.paku.myus.api.community.repository.CommunityRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CommunityService(
    private val communityRepository: CommunityRepository
) {

    fun postCommunity(communityRequestDto: CommunityRequestDto) {
        communityRepository.save(communityRequestDto.let(CommunityEntityMapper::mapToData))
    }

    fun getCommunity(perPage: Int): List<CommunityResponseDto> {
        return communityRepository.findAll(Pageable.ofSize(perPage))
            .toList().map(CommunityMapper::mapToData)
    }
}