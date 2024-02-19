
import com.example.ktschool.config.JwtTokenProvider
import com.example.ktschool.domain.dto.MyUserDetail
import com.example.ktschool.logger
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.server.ResponseStatusException

@SpringBootTest
@ContextConfiguration(classes = [JwtTokenProvider::class])
class JwtTokenProviderTest(
    @Autowired
    var tokenProvider: JwtTokenProvider
) : AnnotationSpec() {

    @Test
    fun `토큰을 발급하고 검증 해본다`() {
        val username = "admin1"
        val password = ""
        val role = listOf(
            GrantedAuthority { "ROLE_ADMIN" })
        val principal = MyUserDetail(
            1L, username, password, role)
        val authentication = UsernamePasswordAuthenticationToken(principal, null, role)
        logger.info { "authentication = $authentication" }
        val token = tokenProvider.createToken((authentication as Authentication))
        token.grantType shouldBe "Bearer"
        val result = tokenProvider.validateToken(token.accessToken)
        result shouldBe true
    }

    @Test
    fun `ERR) 임의토큰은 실패한다`() {
        shouldThrow<ResponseStatusException> {
            tokenProvider.validateToken("eyJhbGciOi")
        }.let { e ->
            e.statusCode shouldBe HttpStatus.UNAUTHORIZED
        }
    }
}