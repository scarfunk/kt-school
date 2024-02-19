package com.example.ktschool.application.service

import com.example.ktschool.application.port.`in`.CreateSchoolUseCase
import com.example.ktschool.application.port.out.CommandSchoolPort
import com.example.ktschool.application.port.out.LoadAdminPort
import org.springframework.stereotype.Service

@Service
class SchoolService(
    private var loadAdminPort: LoadAdminPort,
    private var commandSchoolPort: CommandSchoolPort,
) : CreateSchoolUseCase {
    override fun createSchool(
        command: CreateSchoolUseCase.Command,
        adminId: Long
    ): CreateSchoolUseCase.Result {
        val foundAdmin = loadAdminPort.findAdmin(adminId)
        val savedSchool = commandSchoolPort.createSchool(command.toEntity(), foundAdmin)
        return CreateSchoolUseCase.Result(savedSchool.id, savedSchool.name)
    }
}