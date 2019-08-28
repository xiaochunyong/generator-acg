package me.ely.domain.request

data class UserSearchRequestModel (
        val username: String?
) : BaseSearchRequestModel()