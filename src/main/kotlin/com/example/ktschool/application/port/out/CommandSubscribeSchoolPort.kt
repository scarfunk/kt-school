package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.SchoolEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentEntity
import com.example.ktschool.adapter.out.persistence.entity.StudentSchoolSubscriptionEntity

interface CommandSubscribeSchoolPort {
    fun subscribe(school: SchoolEntity, student: StudentEntity): StudentSchoolSubscriptionEntity
}
