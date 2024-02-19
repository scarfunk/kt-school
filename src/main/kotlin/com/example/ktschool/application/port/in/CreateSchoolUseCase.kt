package com.example.ktschool.application.port.`in`

import com.example.ktschool.adapter.out.persistence.entity.SchoolEntity

interface CreateSchoolUseCase {
    fun createSchool(command: Command, adminId: Long): Result

    data class Command(
        val name: String,
        val location: String,
    ) {
        fun toEntity(): SchoolEntity {
            return SchoolEntity(name, location)
        }
    }

    data class Result(
        val id: Long,
        val name: String,
    )
}
