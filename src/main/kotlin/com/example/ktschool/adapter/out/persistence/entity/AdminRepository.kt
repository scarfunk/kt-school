package com.example.ktschool.adapter.out.persistence.entity

import com.example.ktschool.adapter.out.persistence.entity.AdminEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : JpaRepository<AdminEntity, Long> {
    fun findAllByUsername(username: String): List<AdminEntity>
}