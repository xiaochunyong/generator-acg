package me.ely.domain.document

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.Id

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-06-26
 */
@Document(collection = "order")
data class Order(
        @Id val id: String?,
        val no: String,
        val productId: String,
        val productName: String,
        val limit: Double,
        val createDate: Date = Date(),
        val createUserId: Long,
        val createUserName: String
)