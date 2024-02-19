package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.SchoolFeedEntity

interface LoadSchoolFeedPort {
    fun findSchoolFeedBySchoolId(id: Long): List<SchoolFeedEntity>
}
