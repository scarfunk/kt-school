package com.example.ktschool.domain.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class AdminUserDetail(
    var id: Long,
    username: String,
    password: String,
    authorities: Collection<GrantedAuthority>
) : User(username, password, authorities)