package com.example.ktschool.application.service

import com.example.ktschool.application.port.`in`.SubscribeSchoolUseCase
import com.example.ktschool.application.port.out.CommandSubscribeSchoolPort
import com.example.ktschool.application.port.out.LoadStudentPort
import com.example.ktschool.application.port.out.LoadSubscriptionPort
import org.springframework.stereotype.Service

@Service
class SubscribeService(
    private val loadStudentPort: LoadStudentPort,
    private val loadSchoolPort: LoadSchoolPort,
    private val loadSubscriptionPort: LoadSubscriptionPort,
    private val commandSubscribeSchoolPort: CommandSubscribeSchoolPort
) : SubscribeSchoolUseCase {
    override fun subscribe(command: SubscribeSchoolUseCase.Command): SubscribeSchoolUseCase.Result {
        // validate if school exists
        val student = loadStudentPort.findStudent(command.studentId)
        val school = loadSchoolPort.findSchool(command.schoolId)
        val exist = loadSubscriptionPort.find(command.studentId, command.schoolId)
        if (exist != null) {
            throw IllegalArgumentException("already subscribed")
        }
        val subscription = commandSubscribeSchoolPort.subscribe(
            school,
            student
        )
        return SubscribeSchoolUseCase.Result(
            subscription.id,
        )
    }

    override fun unSubscribe(command: SubscribeSchoolUseCase.Command): SubscribeSchoolUseCase.Result {
        TODO("Not yet implemented")
    }
}