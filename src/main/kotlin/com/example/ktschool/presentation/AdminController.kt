package com.example.ktschool.presentation

import com.example.ktschool.application.AdminService
import com.example.ktschool.application.MakeSchoolDto
import com.example.ktschool.application.SchoolService
import com.example.ktschool.config.JwtTokenProvider
import com.example.ktschool.config.TokenInfo
import com.example.ktschool.domain.AdminEntity
import com.example.ktschool.domain.dto.AdminRequest
import com.example.ktschool.domain.dto.AdminResponse
import com.example.ktschool.domain.dto.AdminUserDetail
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
class AdminController(
    val adminService: AdminService,
    val schoolService: SchoolService,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    // test GET hello world
    @GetMapping("/hello")
    fun helloWorld(): String {
        logger.info { "found!!" }
        return "Hello World"
    }

    @PostMapping("/admin/register")
    fun createAdmin(@RequestBody dto: AdminRequest.CreateAdminRequest): TokenInfo {
        val entity = AdminEntity(username = dto.username)
        val created = adminService.saveAdmin(entity)
        val authenticationToken = UsernamePasswordAuthenticationToken(
            created.username, ""
        )
        // 여기가 내부적으로 loadUserByUsername 을 호출.
        val authentication = authenticationManager.authenticate(authenticationToken)
        logger.info { "authentication: $authentication" }
        // jwt token 생성
        return jwtTokenProvider.createToken(authentication)
    }

    @GetMapping("/admin/{username}")
    fun getAdminByUsername(
        @PathVariable username: String,
        @AuthenticationPrincipal principal: UserDetails?,
    ): ResponseEntity<List<AdminResponse.AdminResponse>> {
        logger.info { "principal: ${principal?.username}" }
        val admin = adminService.findAllAdminByUsername(username)
        return ResponseEntity.ok().body(admin.map {
            AdminResponse.AdminResponse(
                it.id,
                it.username,
                it.createdDate!!,
                it.updatedDate!!
            )
        });
    }

    @PostMapping("/admin/make-school")
    fun makeSchool(
        @RequestBody dto: AdminRequest.MakeSchoolRequest,
        @AuthenticationPrincipal principal: AdminUserDetail
    ): ResponseEntity<AdminResponse.CreateResponse> {
        val admin = adminService.findAdminById(principal.id)
        val school = schoolService.makeSchool(
            MakeSchoolDto(dto.name, dto.location),
            admin.id!!
        )
        return ResponseEntity.ok().body(
            AdminResponse.CreateResponse(
                school.id!!,
            )
        )
    }
}