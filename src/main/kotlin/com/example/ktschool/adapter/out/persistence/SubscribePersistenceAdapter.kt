package com.example.ktschool.adapter.out.persistence

import com.example.ktschool.adapter.out.persistence.entity.SchoolEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentSchoolSubscriptionEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentSchoolSubscriptionRepository
import com.example.ktschool.application.port.out.CommandSubscribeSchoolPort
import com.example.ktschool.application.port.out.LoadSubscriptionPort
import org.springframework.stereotype.Component

@Component
class SubscribePersistenceAdapter(
    private val repository: StudentSchoolSubscriptionRepository,
) : CommandSubscribeSchoolPort,
    LoadSubscriptionPort {
    override fun subscribe(
        school: SchoolEntity,
        student: StudentEntity
    ): StudentSchoolSubscriptionEntity {
        val subscription = StudentSchoolSubscriptionEntity(
            school = school,
            student = student
        )
        return repository.save(subscription)
    }

    override fun find(studentId: Long, schoolId: Long): StudentSchoolSubscriptionEntity? {
        return repository.findByStudentIdAndSchoolId(studentId, schoolId)
    }


}