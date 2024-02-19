package com.example.ktschool.adapter.out.persistence.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<StudentEntity, Long> {
    fun findByUsername(username: String): List<StudentEntity>
}