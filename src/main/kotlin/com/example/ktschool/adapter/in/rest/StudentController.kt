package com.example.ktschool.adapter.`in`.rest

import com.example.ktschool.application.port.`in`.*
import com.example.ktschool.application.port.webdto.StudentRequest
import com.example.ktschool.domain.dto.MyUserDetail
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Student", description = "학생 API")
class StudentController(
    private val createStudentUseCase: CreateStudentUseCase,
    private val createTokenUseCase: CreateTokenUseCase,
    private val subscribeSchoolUseCase: SubscribeSchoolUseCase,
    private val findFeedUseCase: FindFeedUseCase,
    private val findNewsFeedUseCase: FindNewsFeedUseCase
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

    @GetMapping("/student/school-feed/{schoolId}")
    fun getSchoolFeed(
        @PathVariable schoolId: Long,
        @AuthenticationPrincipal principal: MyUserDetail
    ): List<FindFeedUseCase.Result> {
        return findFeedUseCase.findFeedsBySchoolId(schoolId, principal.id)
    }

    @GetMapping("/student/my-news-feed")
    fun getNewsFeed(@AuthenticationPrincipal principal: MyUserDetail): List<FindNewsFeedUseCase.Result> {
        return findNewsFeedUseCase.findMyNewsFeed(principal.id)
    }
}