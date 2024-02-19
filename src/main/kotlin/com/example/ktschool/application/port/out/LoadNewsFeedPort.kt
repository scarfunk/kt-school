package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.StudentNewsFeedEntity

interface LoadNewsFeedPort {
    fun findAllMyNews(studentId: Long): List<StudentNewsFeedEntity>
}
