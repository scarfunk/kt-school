package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.StudentEntity

interface LoadStudentPort {
    fun findStudent(id: Long): StudentEntity
}
