package com.example.ktschool.adapter.out.persistence.entity

import com.example.ktschool.adapter.out.persistence.entity.SchoolEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SchoolRepository : JpaRepository<SchoolEntity, Long> {

}