package com.example.ktschool.application.port.webdto

import com.example.ktschool.application.port.`in`.CreateAdminUseCase
import com.example.ktschool.application.port.`in`.CreateSchoolUseCase
import com.example.ktschool.application.port.`in`.PostSchoolFeedUseCase
import kotlinx.serialization.Serializable

class AdminRequest {
    @Serializable
    data class CreateAdminRequest(
        val username: String,
    ) {
        fun toCommand(): CreateAdminUseCase.Command {
            return CreateAdminUseCase.Command(username)
        }
    }

    @Serializable
    data class MakeSchoolRequest(
        val name: String,
        val location: String
    ) {

        fun toCommand(): CreateSchoolUseCase.Command {
            return CreateSchoolUseCase.Command(name, location)
        }
    }
    @Serializable
    data class PostFeedRequest(
        val content: String,
    ) {
        fun toCommand(): PostSchoolFeedUseCase.Command {
            return PostSchoolFeedUseCase.Command(content)
        }
    }
}
