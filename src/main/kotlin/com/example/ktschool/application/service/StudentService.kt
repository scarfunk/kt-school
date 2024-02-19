package com.example.ktschool.application.service

import com.example.ktschool.application.port.`in`.CreateStudentUseCase
import com.example.ktschool.application.port.out.CommandStudentPort
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val commandStudentPort: CommandStudentPort,
) : CreateStudentUseCase {
    override fun createStudent(command: CreateStudentUseCase.Command): CreateStudentUseCase.Result {
        val student = commandStudentPort.createStudent(command.username)
        return CreateStudentUseCase.Result(student.id, student.username)
    }
}