package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.StudentEntity

interface CommandStudentPort {
    fun createStudent(username: String): StudentEntity
}
