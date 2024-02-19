package com.example.ktschool.adapter.out.persistence

import com.example.ktschool.adapter.out.persistence.entity.SchoolFeedEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentNewsFeedEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentNewsFeedRepository
import com.example.ktschool.application.port.out.CommandNewsFeedPort
import com.example.ktschool.application.port.out.LoadNewsFeedPort

//@Component
class NewsFeedPersistenceAdapter(
    private val studentNewsFeedRepository: StudentNewsFeedRepository,
) : CommandNewsFeedPort, LoadNewsFeedPort {
    override fun postNewsFeed(
        student: StudentEntity,
        feed: SchoolFeedEntity
    ): StudentNewsFeedEntity {
        val newsFeed = StudentNewsFeedEntity(
            student = student,
            feed = feed
        )
        return studentNewsFeedRepository.save(newsFeed)
    }

    override fun postNewsFeed(studentId: Long, feedId: Long) {
        TODO("Not yet implemented")
    }

    override fun findAllMyNews(studentId: Long): List<StudentNewsFeedEntity> {
        return studentNewsFeedRepository.findAllByStudentId(studentId)
    }
}