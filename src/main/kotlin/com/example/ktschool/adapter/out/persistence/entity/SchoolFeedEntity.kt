package com.example.ktschool.adapter.out.persistence.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
class SchoolFeedEntity(
    @Column
    var content: String,

    @ManyToOne
    @JoinColumn(name = "school_id", foreignKey = ForeignKey(name = "none"))
    var school: SchoolEntity? = null,

    @OneToMany(mappedBy = "feed")
    var studentNewsFeed: List<StudentNewsFeedEntity>? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L;

    @CreationTimestamp
    var createdDate: Long? = null;

    @UpdateTimestamp
    var updatedDate: Long? = null;
}