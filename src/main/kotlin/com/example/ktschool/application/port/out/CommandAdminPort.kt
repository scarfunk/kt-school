package com.example.ktschool.application.port.out

import com.example.ktschool.adapter.out.persistence.entity.AdminEntity

interface CommandAdminPort {
    fun createAdmin(username: String): AdminEntity
}
