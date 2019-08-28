package me.ely.domain.request

data class UserLoginRequest (
    val mobile: String,
    val captcha: String
)