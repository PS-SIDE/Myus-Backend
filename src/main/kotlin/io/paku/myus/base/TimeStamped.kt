package io.paku.myus.base

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class TimeStamped {

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
}