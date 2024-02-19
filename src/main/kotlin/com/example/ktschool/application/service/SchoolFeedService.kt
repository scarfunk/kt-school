package com.example.ktschool.application.service

import com.example.ktschool.application.port.`in`.FindFeedUseCase
import com.example.ktschool.application.port.`in`.PostSchoolFeedUseCase
import com.example.ktschool.application.port.out.*
import com.example.ktschool.logger
import org.springframework.stereotype.Service

@Service
class SchoolFeedService(
    private val loadAdminPort: LoadAdminPort,
    private val commandSchoolFeedPort: CommandSchoolFeedPort,
    private val loadSchoolFeedPort: LoadSchoolFeedPort,
    private val loadSubscriptionPort: LoadSubscriptionPort,
    private val commandNewsFeedPort: CommandNewsFeedPort
) : PostSchoolFeedUseCase, FindFeedUseCase {
    override fun postFeed(
        command: PostSchoolFeedUseCase.Command,
        adminId: Long
    ): PostSchoolFeedUseCase.Result {
        val foundAdmin = loadAdminPort.findAdmin(adminId)
        val school = foundAdmin.school;
        val savedFeed = commandSchoolFeedPort.postFeed(command.toEntity(), school!!)
        loadSubscriptionPort.findBySchoolId(school.id).forEach {
            logger.info("Posting feed to student ${it.student?.id}")
            commandNewsFeedPort.postNewsFeed(
                it.student?.id!!,
                savedFeed.id,
            )
        }
        return PostSchoolFeedUseCase.Result(savedFeed.id)
    }

    override fun findFeedsBySchoolId(
        schoolId: Long,
        studentId: Long
    ): List<FindFeedUseCase.Result> {
        loadSubscriptionPort.find(studentId, schoolId)
            ?: throw IllegalArgumentException("Student is not subscribed to this school")
        return loadSchoolFeedPort.findSchoolFeedBySchoolId(schoolId).map {
            FindFeedUseCase.Result(it.id, it.content)
        }
    }
}