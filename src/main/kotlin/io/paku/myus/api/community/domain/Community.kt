package io.paku.myus.api.community.domain

import io.paku.myus.base.TimeStamped
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "community")
class Community(
    category: String,
    title: String,
    content: String,
    images: List<String>?
): TimeStamped() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var category: String = category

    @Column(nullable = false)
    var title: String = title

    @Column(nullable = true)
    @ElementCollection
    var images: List<String>? = images

    @Column(nullable = false)
    var content: String = content

    @Column(nullable = false)
    var viewCount: Int = 0

    @Column(nullable = false)
    var commentCount: Int = 0

    @Column(nullable = false)
    @ColumnDefault("false")
    var isBookmarked: Boolean = false
}