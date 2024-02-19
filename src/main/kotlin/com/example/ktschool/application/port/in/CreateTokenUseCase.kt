package com.example.ktschool.application.port.`in`

interface CreateTokenUseCase {
    fun createToken(command: Command): Result

    data class Command(
        val username: String,
    )

    data class Result(
        val grantType: String,
        val accessToken: String,
    )
}
