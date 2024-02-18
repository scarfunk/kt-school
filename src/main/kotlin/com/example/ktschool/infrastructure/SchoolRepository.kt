package com.example.ktschool.infrastructure

import com.example.ktschool.domain.SchoolEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SchoolRepository : JpaRepository<SchoolEntity, Long> {

}