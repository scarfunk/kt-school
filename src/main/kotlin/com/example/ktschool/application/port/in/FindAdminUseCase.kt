package com.example.ktschool.application.port.`in`

import com.example.ktschool.adapter.out.persistence.entity.SchoolEntity

interface FindAdminUseCase {
    fun findAdminsByUserName(username: String): List<Result>
    fun findAdminById(id: Long): Result

    data class Result(
        val id: Long,
        val username: String,
        val school: SchoolEntity?
    )
}
