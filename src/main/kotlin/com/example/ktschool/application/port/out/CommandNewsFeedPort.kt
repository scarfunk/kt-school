package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.SchoolFeedEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentNewsFeedEntity

interface CommandNewsFeedPort {
    fun postNewsFeed(student: StudentEntity, feed: SchoolFeedEntity): StudentNewsFeedEntity
    fun postNewsFeed(studentId: Long, feedId: Long): Unit
}
