package com.example.ktschool.adapter.out.persistence

import com.example.ktschool.adapter.out.persistence.entity.SchoolEntity
import com.example.ktschool.adapter.out.persistence.entity.SchoolFeedEntity
import com.example.ktschool.adapter.out.persistence.entity.SchoolFeedRepository
import com.example.ktschool.application.port.out.CommandSchoolFeedPort
import com.example.ktschool.application.port.out.LoadSchoolFeedPort
import org.springframework.stereotype.Component

@Component
class SchoolFeedPersistenceAdapter(
    private val schoolFeedRepository: SchoolFeedRepository,
) : CommandSchoolFeedPort, LoadSchoolFeedPort {
    override fun postFeed(feed: SchoolFeedEntity, school: SchoolEntity): SchoolFeedEntity {
        feed.school = school
        school.feeds?.add(feed)
        return schoolFeedRepository.save(feed)
    }

    override fun updateFeed(feed: SchoolFeedEntity, school: SchoolEntity): SchoolFeedEntity {
        TODO("Not yet implemented")
    }

    override fun deleteFeed(feed: SchoolFeedEntity, school: SchoolEntity): SchoolFeedEntity {
        TODO("Not yet implemented")
    }

    override fun findSchoolFeedBySchoolId(id: Long): List<SchoolFeedEntity> {
        return schoolFeedRepository.findSchoolFeedBySchoolId(id)
    }

}