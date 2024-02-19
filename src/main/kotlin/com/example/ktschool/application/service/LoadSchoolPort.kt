package com.example.ktschool.application.service

import com.example.ktschool.adapter.out.persistence.entity.SchoolEntity

interface LoadSchoolPort {
    fun findSchool(id: Long): SchoolEntity
}
