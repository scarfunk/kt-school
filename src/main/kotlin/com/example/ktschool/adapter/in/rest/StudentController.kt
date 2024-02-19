package com.example.ktschool.adapter.`in`.rest

import com.example.ktschool.application.port.`in`.CreateStudentUseCase
import com.example.ktschool.application.port.`in`.CreateTokenUseCase
import com.example.ktschool.application.port.`in`.SubscribeSchoolUseCase
import com.example.ktschool.application.port.webdto.StudentRequest
import com.example.ktschool.domain.dto.MyUserDetail
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StudentController(
    private val createStudentUseCase: CreateStudentUseCase,
    private val createTokenUseCase: CreateTokenUseCase,
    private val subscribeSchoolUseCase: SubscribeSchoolUseCase,
) {
    @PostMapping("/student/register")
    fun createStudent(@RequestBody dto: StudentRequest.CreateStudentRequest): CreateTokenUseCase.Result {
        val created = createStudentUseCase.createStudent(dto.toCommand())
        return createTokenUseCase.createToken(CreateTokenUseCase.Command(created.username))
    }

    @PostMapping("/student/subscribe")
    fun subscribeSchool(
        @RequestBody dto: StudentRequest.SubscribeSchoolRequest,
        @AuthenticationPrincipal principal: MyUserDetail
    ): SubscribeSchoolUseCase.Result {
        dto.studentId = principal.id
        return subscribeSchoolUseCase.subscribe(dto.toCommand())
    }
}