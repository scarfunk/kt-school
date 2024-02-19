package com.example.ktschool.application.port.`in`

import com.example.ktschool.adapter.out.persistence.entity.SchoolFeedEntity

interface PostSchoolFeedUseCase {
    fun postFeed(command: Command, adminId: Long): Result

    data class Command(
        val content: String,
    ) {
        fun toEntity(): SchoolFeedEntity {
            return SchoolFeedEntity(content)
        }
    }

    data class Result(
        val id: Long,
    )
}
