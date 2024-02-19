package com.example.ktschool.adapter.out.persistence

import com.example.ktschool.adapter.out.persistence.entity.AdminEntity
import com.example.ktschool.adapter.out.persistence.entity.SchoolEntity
import com.example.ktschool.adapter.out.persistence.entity.SchoolRepository
import com.example.ktschool.application.port.out.CommandSchoolPort
import com.example.ktschool.application.port.out.LoadSchoolPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class SchoolPersistenceAdapter(
    private val schoolRepository: SchoolRepository,
) : CommandSchoolPort, LoadSchoolPort {
    override fun createSchool(school: SchoolEntity, admin: AdminEntity): SchoolEntity {
        school.admin = admin
        admin.school = school
        return schoolRepository.save(school)
    }

    override fun findSchool(id: Long): SchoolEntity {
        return schoolRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("school not found")
    }

}