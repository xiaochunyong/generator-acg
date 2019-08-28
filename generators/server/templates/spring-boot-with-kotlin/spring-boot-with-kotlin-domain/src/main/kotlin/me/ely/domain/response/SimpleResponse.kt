package me.ely.domain.response

data class SimpleResponse(
    val code: Int,
    val message: String
) : BaseResponse()
