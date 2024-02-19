package com.example.ktschool.application

import com.example.ktschool.application.port.`in`.CreateAdminUseCase
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class AdminServiceTest(@Autowired val createAdminUseCase: CreateAdminUseCase) {

    @Test
    @Transactional
    fun saveAdmin() {
        val admin =
            createAdminUseCase.createAdmin(CreateAdminUseCase.Command("admin1"))
        admin.id shouldNotBe null
    }
}