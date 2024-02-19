package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.AdminEntity

interface LoadAdminPort {
    fun findAdmin(username: String): List<AdminEntity>
    fun findAdmin(id: Long): AdminEntity
}
