package me.ely.domain.request

import java.util.*

data class UserRegisterRequest (
    val mobile: String,
    val name: String,
    val age: Int,
    val height: Double,
    val birthday: Date?,
    val captcha: String
)