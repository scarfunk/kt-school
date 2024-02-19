package com.example.ktschool.api

import com.example.ktschool.adapter.out.persistence.entity.AdminEntity
import com.example.ktschool.adapter.out.persistence.entity.AdminRepository
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerMvcMockTest(
    @MockkBean
    private var adminRepository: AdminRepository,

    @Autowired
    private var mockMvc: MockMvc
) : AnnotationSpec() {

    // test GET hello world
    @Test
    fun `permit all uri 는 통과`() {
        val result = mockMvc.perform(get("/hello"))
            .andExpect(status().isOk)
            .andReturn()
        result.response.contentAsString shouldBe "Hello World"
    }

    @Test
    fun `admin uri 는 실패`() {
        mockMvc.perform(get("/admin/admin1"))
            .andExpect(status().isForbidden)
            .andReturn()
    }

    @Test
    fun `가입 성공`() {
        // mockk 를 사용
        every { adminRepository.save(any()) } returns AdminEntity("admin1")
        every {
            adminRepository.findAllByUsername(any())
        } returns listOf(
            AdminEntity(
                "admin1",
            )
        )
        mockMvc.post("/admin/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"username": "test_admin"}"""
        }.andExpect {
            status { isOk() }
            MockMvcResultMatchers.jsonPath("$.accessToken").exists()
        }.andDo { print() }
    }

    @Test
    fun `가입 성공 후 토큰 받은것으로 get 호출`() {
        // mockk 를 사용
        every { adminRepository.save(any()) } returns AdminEntity("admin1")
        every { adminRepository.findAllByUsername(any()) } returns listOf(
            AdminEntity(
                "admin1",
            )
        )
        val result = mockMvc.post("/admin/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"username": "test_admin"}"""
        }.andExpect {
            status { isOk() }
        }.andReturn()

        val resp = result.response.contentAsString
        val token = JSONObject(resp).getString("accessToken")
        token shouldNotBe null
        mockMvc.get("/admin/test_admin") {
            header("Authorization", "Bearer $token")
        }.andExpect {
            status { isOk() }
        }.andDo { print() }
    }
}