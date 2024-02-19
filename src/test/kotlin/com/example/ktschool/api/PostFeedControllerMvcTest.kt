package com.example.ktschool.api

import com.example.ktschool.application.port.webdto.AdminRequest
import com.example.ktschool.logger
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import javax.sql.DataSource


@SpringBootTest
@AutoConfigureMockMvc
class PostFeedControllerMvcTest(
    @Autowired
    private var mockMvc: MockMvc,
    @Autowired
    private var dataSource: DataSource
) : AnnotationSpec() {
    val json = Json { prettyPrint = true }

    // 각 테스트의 저장소 초기화를 위해 사용한다.
    // @Transactional 도 마찬가지로 kotest 에서는 작동하지 않는다.
    // 혹은 extension(SpringExtension) 를 쓴다.
    @BeforeEach
    @AfterAll
    fun setUp() {
        dataSource.connection.use { connection ->
            ScriptUtils.executeSqlScript(
                connection,
                ClassPathResource("/sql/truncate-for-test.sql")
            )
        }
    }

    @Test
    fun `가입 성공 후 토큰 받은것으로 후 학교 생성 후 피드 생성`() {
        // 어드민 생성
        val result = mockMvc.post("/admin/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"username": "test_admin"}"""
        }.andExpect {
            status { isOk() }
        }.andReturn()

        val resp = result.response.contentAsString
        val token = JSONObject(resp).getString("accessToken")
        val schoolReq = AdminRequest.MakeSchoolRequest("test_school", "test_location")
        val encodeString = json.encodeToString(schoolReq)
        logger.info { "encodeString: $encodeString" }

        // 학교 생성
        val schoolResp = mockMvc.post("/admin/make-school") {
            header("Authorization", "Bearer $token")
            contentType = MediaType.APPLICATION_JSON
            content = encodeString
        }.andExpect {
            status { isOk() }
        }.andDo { print() }.andReturn().response.contentAsString
        val schoolId = JSONObject(schoolResp).getLong("id")

        // 피드 생성
        mockMvc.post("/admin/post-feed") {
            header("Authorization", "Bearer $token")
            contentType = MediaType.APPLICATION_JSON
            content = AdminRequest.PostFeedRequest("hello world")
                .let { json.encodeToString(it) }
        }.andExpect {
            status { isOk() }
        }.andDo { print() }

        // 학생 생성
        val studentResp = mockMvc.post("/student/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """{"username": "test_student"}"""
        }.andExpect {
            status { isOk() }
        }.andReturn().response.contentAsString
        val studentToken = JSONObject(studentResp).getString("accessToken")

        // 학교 구독
        mockMvc.post("/student/subscribe") {
            header("Authorization", "Bearer $studentToken")
            contentType = MediaType.APPLICATION_JSON
            content = """{"schoolId": $schoolId}"""
        }.andExpect {
            status { isOk() }
        }

        // 학교 피드 조회
        mockMvc.get("/student/school-feed/$schoolId") {
            header("Authorization", "Bearer $studentToken")
        }.andExpect {
            status { isOk() }
        }.andDo { print() }
    }
}