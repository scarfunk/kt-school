package com.example.ktschool.application.port.`in`

interface FindNewsFeedUseCase {
    fun findMyNewsFeed(studentId: Long): List<Result>

    data class Result(
        val id: Long,
        val content: String,
    )
}
