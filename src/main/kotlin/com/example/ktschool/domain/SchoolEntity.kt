package com.example.ktschool.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp

@Entity
class SchoolEntity(
    @Column
    var name: String,

    @Column
    var location: String,

    // 학교 생성에는 관리자가 필수이므로 주생성자.
    @OneToOne
    @JoinColumn(name = "admin_id", foreignKey = ForeignKey(name = "none"))
    var admin: AdminEntity? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L;

    @CreationTimestamp
    var createdDate: Long? = null;

    @UpdateTimestamp
    var updatedDate: Long? = null;
}