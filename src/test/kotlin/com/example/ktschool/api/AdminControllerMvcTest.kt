package com.example.ktschool.api

import com.example.ktschool.domain.dto.AdminRequest
import com.example.ktschool.logger
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import javax.sql.DataSource


private val json = Json { prettyPrint = true }

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerMvcTest(
    @Autowired
    private var mockMvc: MockMvc,
    @Autowired
    private var dataSource: DataSource
) : AnnotationSpec() {

    // 각 테스트의 저장소 초기화를 위해 사용한다.
    // @Transactional 도 마찬가지로 kotest 에서는 작동하지 않는다.
    // 혹은 extension(SpringExtension) 를 쓴다.
    @BeforeEach
    fun setUp() {
        dataSource.connection.use { connection ->
            ScriptUtils.executeSqlScript(
                connection,
                ClassPathResource("/sql/truncate-for-test.sql")
            )
        }
    }

    @Test
    fun `가입 성공 후 토큰 받은것으로 후 학교 생성`() {
        val result = mockMvc.post("/admin/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"username": "test_admin"}"""
        }.andExpect {
            status { isOk() }
        }.andReturn()

        val resp = result.response.contentAsString
        val mapper = ObjectMapper().readTree(resp)
        val token = mapper.get("accessToken").asText()
        val schoolReq = AdminRequest.MakeSchoolRequest("test_school", "test_location")
        val encodeString = json.encodeToString(schoolReq)
        logger.info { "encodeString: $encodeString" }

        mockMvc.post("/admin/make-school") {
            header("Authorization", "Bearer $token")
            contentType = MediaType.APPLICATION_JSON
            content = encodeString
        }.andExpect {
            status { isOk() }
        }.andDo { print() }
    }
}