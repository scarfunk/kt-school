package com.example.ktschool.adapter.out.persistence.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentNewsFeedRepository :
    JpaRepository<StudentNewsFeedEntity, Long> {
    fun findAllByStudentId(studentId: Long): List<StudentNewsFeedEntity>
}