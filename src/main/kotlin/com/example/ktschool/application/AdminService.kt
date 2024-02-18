package com.example.ktschool.application

import com.example.ktschool.domain.AdminEntity
import com.example.ktschool.infrastructure.AdminRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AdminService(var adminRepository: AdminRepository) {
    fun findAllAdminByUsername(username: String): List<AdminEntity> {
        val admin = adminRepository.findAllByUsername(username)
        return admin
    }

    fun saveAdmin(admin: AdminEntity): AdminEntity {
        return adminRepository.save(admin)
    }

    fun findAdminById(id: Long): AdminEntity {
        val res = adminRepository.findByIdOrNull(id)
        require(res != null) { "admin not found" }
        return res;
    }
}