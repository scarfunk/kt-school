package com.example.ktschool.application.service

import com.example.ktschool.application.port.`in`.CreateStudentUseCase
import com.example.ktschool.application.port.`in`.FindNewsFeedUseCase
import com.example.ktschool.application.port.out.CommandStudentPort
import com.example.ktschool.application.port.out.LoadNewsFeedPort
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val commandStudentPort: CommandStudentPort,
    private val loadNewsFeedPort: LoadNewsFeedPort
) : CreateStudentUseCase, FindNewsFeedUseCase {
    override fun createStudent(command: CreateStudentUseCase.Command): CreateStudentUseCase.Result {
        val student = commandStudentPort.createStudent(command.username)
        return CreateStudentUseCase.Result(student.id, student.username)
    }

    override fun findMyNewsFeed(studentId: Long): List<FindNewsFeedUseCase.Result> {
        return loadNewsFeedPort.findAllMyNews(studentId).map {
            FindNewsFeedUseCase.Result(it.id, it.feed!!.content)
        }
    }
}