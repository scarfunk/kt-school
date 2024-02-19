package com.example.ktschool.application.port.webdto

import com.example.ktschool.application.port.`in`.CreateStudentUseCase
import com.example.ktschool.application.port.`in`.SubscribeSchoolUseCase

class StudentRequest {
    data class CreateStudentRequest(
        val username: String,
    ) {
        fun toCommand(): CreateStudentUseCase.Command {
            return CreateStudentUseCase.Command(username)
        }
    }

    data class SubscribeSchoolRequest(
        val schoolId: Long,
    ) {
        var studentId: Long = 0;
        fun toCommand(): SubscribeSchoolUseCase.Command {
            return SubscribeSchoolUseCase.Command(studentId, schoolId)
        }
    }
}
