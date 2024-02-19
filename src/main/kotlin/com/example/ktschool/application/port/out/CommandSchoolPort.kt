package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.AdminEntity
import com.example.ktschool.adapter.out.persistence.entity.SchoolEntity

interface CommandSchoolPort {
    fun createSchool(school: SchoolEntity, admin: AdminEntity): SchoolEntity
}
