package me.ely.domain.dto

import java.util.*

data class UserDto (
        val id: Int,
        val name: String,
        val height: Double,
        val birthday: Date
)