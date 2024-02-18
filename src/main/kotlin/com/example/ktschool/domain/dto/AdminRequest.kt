package com.example.ktschool.domain.dto

import kotlinx.serialization.Serializable

class AdminRequest {
    data class CreateAdminRequest(
        val username: String,
    )

    @Serializable
    data class MakeSchoolRequest (
        val name: String,
        val location: String)
}
