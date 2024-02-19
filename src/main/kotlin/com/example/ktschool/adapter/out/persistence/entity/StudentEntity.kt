package com.example.ktschool.adapter.out.persistence.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
class StudentEntity(
    // 초기 생성에 필요한 것은 주 생성자 로 받는다.
    var username: String,

    @OneToMany(mappedBy = "student")
    var subscribedSchool: List<StudentSchoolSubscriptionEntity>? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L;

    @CreationTimestamp
    var createdDate: Long? = null;

    @UpdateTimestamp
    var updatedDate: Long? = null;
}