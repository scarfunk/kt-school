package com.example.ktschool.adapter.out.persistence

import com.example.ktschool.adapter.out.persistence.entity.AdminEntity
import com.example.ktschool.adapter.out.persistence.entity.AdminRepository
import com.example.ktschool.application.port.out.CommandAdminPort
import com.example.ktschool.application.port.out.LoadAdminPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AdminPersistenceAdapter(
    private val adminRepository: AdminRepository
) : CommandAdminPort,
    LoadAdminPort {
    override fun createAdmin(username: String): AdminEntity {
        return adminRepository.save(AdminEntity(username = username))
    }

    override fun findAdmin(username: String): List<AdminEntity> {
        val admin = adminRepository.findAllByUsername(username)
        return admin;
    }

    override fun findAdmin(id: Long): AdminEntity {
        val admin = adminRepository.findByIdOrNull(id)
        return admin ?: throw IllegalArgumentException("admin not found")
    }

}