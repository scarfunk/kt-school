package com.example.ktschool.adapter.out.persistence.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
class StudentSchoolSubscriptionEntity(
    @ManyToOne
    @JoinColumn(name = "school_id", foreignKey = ForeignKey(name = "none"))
    var school: SchoolEntity? = null,

    @ManyToOne
    @JoinColumn(name = "student_id", foreignKey = ForeignKey(name = "none"))
    var student: StudentEntity? = null,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L;

    @CreationTimestamp
    var createdDate: Long? = null;

    @UpdateTimestamp
    var updatedDate: Long? = null;
}