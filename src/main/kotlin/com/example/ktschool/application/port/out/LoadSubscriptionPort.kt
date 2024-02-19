package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.StudentSchoolSubscriptionEntity

interface LoadSubscriptionPort {
    fun find(studentId: Long, schoolId: Long): StudentSchoolSubscriptionEntity?

    fun findBySchoolId(schoolId: Long): List<StudentSchoolSubscriptionEntity>
}
