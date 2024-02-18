package com.example.ktschool.infrastructure

import com.example.ktschool.domain.AdminEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : JpaRepository<AdminEntity, Long> {
    fun findAllByUsername(username: String): List<AdminEntity>
}