package me.ely.domain.response

data class SearchResponse<T> (
    val code: Int,
    val message: String,
    val data: List<T>?,
    val pageIndex: Int,
    val pageSize: Int,
    val totalCount: Long
) : BaseResponse() {

    constructor(data: List<T>?, pageIndex: Int, pageSize: Int, totalCount: Long) : this(0, "success", data, pageIndex, pageSize, totalCount)

    /**
     * for ResponseCode.toResponse
     */
    constructor(code: Int, message: String) : this(code, message, null, 0, 20, 0)

}