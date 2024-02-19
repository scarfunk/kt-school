package com.example.ktschool.adapter.out.persistence.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
class StudentNewsFeedEntity(
    @Column(name = "student_id")
    var studentId: Long? = null,

    @Column(name = "feed_id")
    var feedId: Long? = null,

    @ManyToOne
    @JoinColumn(
        name = "student_id",
        foreignKey = ForeignKey(name = "none"),
        insertable = false,
        updatable = false
    )
    var student: StudentEntity? = null,

    @ManyToOne
    @JoinColumn(
        name = "feed_id",
        foreignKey = ForeignKey(name = "none"),
        insertable = false,
        updatable = false
    )
    var feed: SchoolFeedEntity? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L;

    @CreationTimestamp
    var createdDate: Long? = null;

    @UpdateTimestamp
    var updatedDate: Long? = null;
}