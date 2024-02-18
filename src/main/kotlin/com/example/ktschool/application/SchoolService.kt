package com.example.ktschool.application

import com.example.ktschool.domain.SchoolEntity
import com.example.ktschool.infrastructure.SchoolRepository
import org.springframework.stereotype.Service

@Service
class SchoolService(
    private val adminService: AdminService,
    private val schoolRepository: SchoolRepository,
) {
    fun makeSchool(data: MakeSchoolDto, adminId: Long): SchoolEntity {
        val foundAdmin =
            adminService.findAdminById(adminId)
        // school 객체 생성
        val school = SchoolEntity(
            name = data.name,
            location = data.location,
            admin =  foundAdmin
        )
        // 양방향의 경우는 admin 객체에 school 객체를 넣어주어야 한다.
        // 연관관계 편의 메소드를 사용하여 양방향 연관관계를 설정한다.
        // 양쪽 repo.save 를 안해주어도 된다.
        foundAdmin.school = school
        return schoolRepository.save(school)
    }
}

class MakeSchoolDto(
    val name: String,
    val location: String,
)
