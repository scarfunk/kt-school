package com.example.ktschool.infrastucture

import com.example.ktschool.domain.AdminEntity
import com.example.ktschool.infrastructure.AdminRepository
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
// 실제 mysql DB 사용하기 위해 NONE 설정 (https://charliezip.tistory.com/21)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminRepositoryTest(@Autowired val adminRepository: AdminRepository) {
    @Test
    fun `admin을 저장하고 조회해본다`() {
        val admin = AdminEntity(username = "admin")
        adminRepository.save(admin)
        val result = adminRepository.findAllByUsername("admin")
        result.size shouldBe 1
    }
}