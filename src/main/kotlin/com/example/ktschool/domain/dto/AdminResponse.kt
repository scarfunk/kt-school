package com.example.ktschool.domain.dto

class AdminResponse {
    data class AdminResponse(
        val id: Long?,
        val username: String,
        val createdDate: Long,
        val updatedDate: Long,
    )

    data class SchoolResponse(
        val id: Long,
        val name: String? = null,
        val location: String,
        val createdDate: Long,
        val updatedDate: Long
    )

    data class CreateResponse(
        var id: Long
    )
}
