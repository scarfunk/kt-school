package com.example.ktschool.adapter.`in`.rest

import com.example.ktschool.application.port.`in`.CreateAdminUseCase
import com.example.ktschool.application.port.`in`.CreateSchoolUseCase
import com.example.ktschool.application.port.`in`.CreateTokenUseCase
import com.example.ktschool.application.port.`in`.FindAdminUseCase
import com.example.ktschool.application.port.webdto.AdminRequest
import com.example.ktschool.application.port.webdto.AdminResponse
import com.example.ktschool.domain.dto.MyUserDetail
import com.example.ktschool.logger
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
class AdminController(
    private val createAdminUseCase: CreateAdminUseCase,
    private val findAdminUseCase: FindAdminUseCase,
    private val createSchoolUseCase: CreateSchoolUseCase,
    private val createTokenUseCase: CreateTokenUseCase,
) {
    // test GET hello world
    @GetMapping("/hello")
    fun helloWorld(): String {
        logger.info { "found!!" }
        return "Hello World"
    }

    @PostMapping("/admin/register")
    fun createAdmin(@RequestBody dto: AdminRequest.CreateAdminRequest): CreateTokenUseCase.Result {
        val createAdmin = createAdminUseCase.createAdmin(dto.toCommand())
        return createTokenUseCase.createToken(CreateTokenUseCase.Command(createAdmin.username))
    }

    @GetMapping("/admin/{username}")
    fun getAdminByUsername(
        @PathVariable username: String,
        @AuthenticationPrincipal principal: UserDetails?,
    ): ResponseEntity<List<AdminResponse.AdminResponse>> {
        logger.info { "principal: ${principal?.username}" }
        val admins = findAdminUseCase.findAdminsByUserName(username)
        return ResponseEntity.ok().body(admins.map {
            AdminResponse.AdminResponse(
                it.id,
                it.username,
            )
        });
    }

    @PostMapping("/admin/make-school")
    fun makeSchool(
        @RequestBody dto: AdminRequest.MakeSchoolRequest,
        @AuthenticationPrincipal principal: MyUserDetail
    ): ResponseEntity<AdminResponse.CreateResponse> {
        val school = createSchoolUseCase.createSchool(dto.toCommand(), principal.id)
        return ResponseEntity.ok().body(
            AdminResponse.CreateResponse(
                school.id,
            )
        )
    }
}