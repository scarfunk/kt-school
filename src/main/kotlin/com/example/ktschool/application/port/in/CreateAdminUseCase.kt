package com.example.ktschool.application.port.`in`

interface CreateAdminUseCase {
    fun createAdmin(command: Command): Result

    data class Command(
        val username: String,
    )

    data class Result(
        val id: Long,
        val username: String,
    )
}
