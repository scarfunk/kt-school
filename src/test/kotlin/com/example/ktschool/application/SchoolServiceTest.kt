package com.example.ktschool.application

import com.example.ktschool.application.port.`in`.CreateAdminUseCase
import com.example.ktschool.application.port.`in`.CreateSchoolUseCase
import com.example.ktschool.application.port.`in`.FindAdminUseCase
import com.example.ktschool.logger
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class SchoolServiceTest(
    @Autowired createSchoolUseCase: CreateSchoolUseCase,
    @Autowired createAdminUseCase: CreateAdminUseCase,
    @Autowired findAdminUseCase: FindAdminUseCase,
) : FunSpec({
    extensions(SpringExtension)
    test("임의 어드민 아이디면 어드민을 찾을수 없다.") {
        shouldThrow<IllegalArgumentException> {
            createSchoolUseCase.createSchool(
                CreateSchoolUseCase.Command("school1", "seoul"),
                9999
            )
        }.let { it.message shouldBe "admin not found" }
    }

    test("어드민 아이디가 있으면 학교를 만들수 있다. > 어드민에서 학교를 조회") {
        val admin =
            createAdminUseCase.createAdmin(CreateAdminUseCase.Command("admin1"))
        logger.info { "admin: ${admin.id}" }
        val result = createSchoolUseCase.createSchool(
            CreateSchoolUseCase.Command("school1", "seoul"),
            admin.id
        )
        logger.info { "result: ${result.name}" }
        result.id shouldNotBe null
        val found = findAdminUseCase.findAdminsByUserName("admin1")
        found.size shouldBe 1
        logger.info { "found: ${found[0].school}" }
        found[0].school?.name shouldBe "school1"
    }
})
