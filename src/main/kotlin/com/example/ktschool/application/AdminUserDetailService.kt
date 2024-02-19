package com.example.ktschool.application

import com.example.ktschool.adapter.out.persistence.entity.AdminRepository
import com.example.ktschool.adapter.out.persistence.entity.StudentRepository
import com.example.ktschool.domain.dto.MyUserDetail
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
    private val studentRepository: StudentRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        val foundAdmin = adminRepository.findAllByUsername(username)
        if (foundAdmin.isNotEmpty()) {
            return MyUserDetail(
                foundAdmin[0].id,
                foundAdmin[0].username,
                passwordEncoder.encode(""),
                listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
            )
        }
        val foundStudent = studentRepository.findByUsername(username)
        if (foundStudent.isNotEmpty()) {
            return MyUserDetail(
                foundStudent[0].id,
                foundStudent[0].username,
                passwordEncoder.encode(""),
                listOf(SimpleGrantedAuthority("ROLE_STUDENT"))
            )
        }
        throw Exception("해당 유저는 없습니다.")

    }
}