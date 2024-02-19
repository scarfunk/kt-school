package com.example.ktschool.adapter.out.persistence.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SchoolFeedRepository :
    JpaRepository<SchoolFeedEntity, Long> {
    fun findSchoolFeedBySchoolId(id: Long): List<SchoolFeedEntity>
}