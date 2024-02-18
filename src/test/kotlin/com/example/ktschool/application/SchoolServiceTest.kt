package com.example.ktschool.application

import com.example.ktschool.domain.AdminEntity
import com.example.ktschool.logger
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class SchoolServiceTest(
    @Autowired schoolService: SchoolService,
    @Autowired adminService: AdminService
) : FunSpec({
    extensions(SpringExtension)
    test("임의 어드민 아이디면 어드민을 찾을수 없다.") {
        shouldThrow<IllegalArgumentException> {
            schoolService.makeSchool(MakeSchoolDto("school1", "seoul"), 9999)
        }.let { it.message shouldBe "admin not found"}
    }

    test("어드민 아이디가 있으면 학교를 만들수 있다. > 어드민에서 학교를 조회") {
        val admin = adminService.saveAdmin(AdminEntity(username = "admin"))
        logger.info {  "admin: ${admin.id}" }
        val result = schoolService.makeSchool(MakeSchoolDto("school1", "seoul"), admin.id!!)
        logger.info {  "result: ${result.name}" }
        result.name shouldBe "school1"
        val found = adminService.findAllAdminByUsername("admin")
        found.size shouldBe 1
        logger.info { "found: ${found[0].school}" }
        found[0].school?.name shouldBe "school1"
    }
})
