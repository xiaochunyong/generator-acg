package me.ely.domain.request

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-06-27
 */
data class UserUpdateRequestModel (
        val username: String,
        val password: String,
        val nickname: String,
        val mobile: String
)