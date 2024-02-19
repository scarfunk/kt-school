package com.example.ktschool.adapter.out.persistence.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentSchoolSubscriptionRepository :
    JpaRepository<StudentSchoolSubscriptionEntity, Long> {
    fun findByStudentIdAndSchoolId(studentId: Long, schoolId: Long): StudentSchoolSubscriptionEntity?
}