package com.example.ktschool.application

import com.example.ktschool.domain.dto.AdminUserDetail
import com.example.ktschool.infrastructure.AdminRepository
import mu.KotlinLogging
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}
@Service
class AdminUserDetailService(
    private val adminRepository: AdminRepository,
//    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        val found = adminRepository.findAllByUsername(username)
        if (found.isEmpty()) {
            throw Exception("해당 유저는 없습니다.")
        }
        return AdminUserDetail(
            found[0].id!!,
            found[0].username,
            passwordEncoder.encode(""),
            listOf(SimpleGrantedAuthority("ROLE_ADMIN" ))
        )
    }
}