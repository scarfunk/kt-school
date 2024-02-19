package com.example.ktschool.presentation

import com.example.ktschool.adapter.`in`.rest.AdminController
import com.example.ktschool.application.port.webdto.AdminRequest
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class AdminControllerJUnitTest(
    @Autowired val adminController: AdminController,
) {
    @Test
    @Transactional
    fun `1명 가입 시키기`() {
        val res = adminController.createAdmin(AdminRequest.CreateAdminRequest("admin1"))
        res.accessToken shouldNotBe null
    }
}