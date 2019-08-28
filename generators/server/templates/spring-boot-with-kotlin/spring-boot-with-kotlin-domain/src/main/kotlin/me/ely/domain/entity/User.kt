package me.ely.domain.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User(
        val username: String,
        val password: String,
        val nickname: String,
        val mobile: String,
        val status: Int
) : BaseEntity()