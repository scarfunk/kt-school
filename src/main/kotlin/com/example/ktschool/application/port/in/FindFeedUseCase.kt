package com.example.ktschool.application.port.`in`

interface FindFeedUseCase {
    fun findFeedsBySchoolId(schoolId: Long, studentId: Long): List<Result>

    data class Result(
        val id: Long,
        val content: String,
    )
}
