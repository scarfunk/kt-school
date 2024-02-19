package com.example.ktschool.adapter.out.persistence

import com.example.ktschool.adapter.out.persistence.entity.StudentEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentRepository
import com.example.ktschool.application.port.out.CommandStudentPort
import com.example.ktschool.application.port.out.LoadStudentPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class StudentPersistenceAdapter(
    private val studentRepository: StudentRepository,
) : CommandStudentPort,
    LoadStudentPort {
    override fun createStudent(username: String): StudentEntity {
        return studentRepository.save(StudentEntity(username = username))
    }

    override fun findStudent(id: Long): StudentEntity {
        val student = studentRepository.findByIdOrNull(id)
        return student ?: throw IllegalArgumentException("student not found")
    }

}