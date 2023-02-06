package io.paku.myus.api.community.repository

import io.paku.myus.api.community.domain.Community
import org.springframework.data.jpa.repository.JpaRepository

interface CommunityRepository : JpaRepository<Community, Long>