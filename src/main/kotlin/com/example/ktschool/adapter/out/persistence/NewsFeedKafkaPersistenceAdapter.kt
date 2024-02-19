package com.example.ktschool.adapter.out.persistence

import com.example.ktschool.adapter.out.persistence.entity.SchoolFeedEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentNewsFeedEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentNewsFeedRepository
import com.example.ktschool.application.port.out.CommandNewsFeedPort
import com.example.ktschool.application.port.out.LoadNewsFeedPort
import com.example.ktschool.config.Sender
import com.example.ktschool.logger
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NewsFeedKafkaPersistenceAdapter(
    private val studentNewsFeedRepository: StudentNewsFeedRepository,
    private val sender: Sender,
) : CommandNewsFeedPort, LoadNewsFeedPort {
    val json = Json { prettyPrint = true }
    override fun postNewsFeed(
        student: StudentEntity,
        feed: SchoolFeedEntity
    ): StudentNewsFeedEntity {
        TODO("Not yet implemented")
    }

    override fun postNewsFeed(
        studentId: Long,
        feedId: Long
    ) {
        val stringify = Json.encodeToString(Sender.Payload(studentId, feedId))
        sender.send(stringify, studentId.toInt())
    }

    private fun saveNewsFeed(
        studentId: Long,
        feedId: Long
    ): StudentNewsFeedEntity {
        val newsFeed = StudentNewsFeedEntity(
            studentId = studentId,
            feedId = feedId,
        )
        logger.info { "Saving news feed: $newsFeed" }
        return studentNewsFeedRepository.save(newsFeed)
    }

    @KafkaListener(topics = ["school-feed"])
    fun listen1(payload: String) {
        logger.info("Received Consume: $payload")
        val `in` = json.decodeFromString(Sender.Payload.serializer(), payload)
        saveNewsFeed(`in`.studentId, `in`.feedId)
    }

    override fun findAllMyNews(studentId: Long): List<StudentNewsFeedEntity> {
        return studentNewsFeedRepository.findAllByStudentId(studentId)
    }
}