package com.example.ktschool.application.service

import com.example.ktschool.application.port.`in`.CreateAdminUseCase
import com.example.ktschool.application.port.`in`.FindAdminUseCase
import com.example.ktschool.application.port.out.CommandAdminPort
import com.example.ktschool.application.port.out.LoadAdminPort
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val commandAdminPort: CommandAdminPort,
    private val loadAdminPort: LoadAdminPort,
) : CreateAdminUseCase,
    FindAdminUseCase {
    override fun createAdmin(command: CreateAdminUseCase.Command): CreateAdminUseCase.Result {
        val savedAdmin = commandAdminPort.createAdmin(command.username)
        return CreateAdminUseCase.Result(savedAdmin.id, savedAdmin.username)
    }

    override fun findAdminsByUserName(username: String): List<FindAdminUseCase.Result> {
        return loadAdminPort.findAdmin(username).map {
            FindAdminUseCase.Result(it.id, it.username, it.school)
        }
    }

    override fun findAdminById(id: Long): FindAdminUseCase.Result {
        return loadAdminPort.findAdmin(id).let {
            FindAdminUseCase.Result(it.id, it.username, it.school)
        }
    }
}