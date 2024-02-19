package com.example.ktschool.application.port.`in`

interface SubscribeSchoolUseCase {
    fun subscribe(command: Command): Result

    fun unSubscribe(command: Command): Result

    data class Command(
        val schoolId: Long,
        val studentId: Long
    )

    data class Result(
        val id: Long,
    )
}
