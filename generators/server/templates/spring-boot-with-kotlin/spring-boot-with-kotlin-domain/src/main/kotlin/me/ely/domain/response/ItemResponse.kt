package me.ely.domain.response

data class ItemResponse<T>(
        val code: Int = 0,
        val message: String = "",
        val data: T? = null
) : BaseResponse() {

    constructor(data: T?) : this(0, "success", data)

    /**
     * for ResponseCode.toResponse
     */
    constructor(code: Int, message: String) : this(code, message, null)

}