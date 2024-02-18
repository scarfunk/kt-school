package com.example.ktschool.application

import com.example.ktschool.domain.AdminEntity
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@org.springframework.boot.test.context.SpringBootTest
class AdminServiceTest(@Autowired val adminService: AdminService) {

    @Test
    @Transactional
    fun saveAdmin() {
        val admin =
            adminService.saveAdmin(AdminEntity(username = "admin"))
        admin.id shouldNotBe null
    }
}