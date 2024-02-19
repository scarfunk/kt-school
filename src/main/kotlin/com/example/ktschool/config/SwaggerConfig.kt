package com.example.ktschool.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@OpenAPIDefinition(
    info = io.swagger.v3.oas.annotations.info.Info(
        title = "KtSchool",
        version = "1.0",
        description = "KtSchool API"
    )
)
class SwaggerConfig(
    val TOKEN_PREFIX: String = "Bearer",
) {
    @Bean
    fun customOpenAPI(): OpenAPI {
        val jwtSchemeName: String = JwtTokenProvider.AUTHORIZATION_HEADER
        val securityRequirement: SecurityRequirement = SecurityRequirement().addList(jwtSchemeName)
        val components = Components()
            .addSecuritySchemes(
                jwtSchemeName, SecurityScheme()
                    .name(jwtSchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme(TOKEN_PREFIX)
                    .bearerFormat("JWT")
            )
        // Swagger UI 접속 후, 딱 한 번만 accessToken을 입력해주면 모든 API에 토큰 인증 작업이 적용됩니다.
        return OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components)
    }
}