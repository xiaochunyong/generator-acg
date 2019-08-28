package me.ely.domain.request

import me.ely.domain.NoArgCons

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-06-26
 */
@NoArgCons
data class OrderSearchRequestModel(
        val no: String?,
        val productName: String?
) : BaseSearchRequestModel()