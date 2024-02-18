package com.example.ktschool.presentation

import com.example.ktschool.domain.dto.AdminRequest
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class AdminControllerTest(
    @Autowired val adminController: AdminController,
) : AnnotationSpec() {
    override fun extensions() = listOf(SpringExtension)
    @Test
    fun `testHelloWorld`(): Unit {
        val result = adminController.helloWorld()
        result shouldBe "Hello World"
    }

    @Test
//    @Transactional
    fun `조회_0개_테스트`() {
        val adminByUsername = adminController.getAdminByUsername("admin", null)
        adminByUsername.body!!.size shouldBe 0
    }

    @Test
    fun `1명 가입 시키고 조회 해보기`() {
        val res = adminController.createAdmin(AdminRequest.CreateAdminRequest("admin"))
        val result = adminController.getAdminByUsername("admin", null)
        result.body!!.size shouldBe 1
    }
}