package me.ely.repository.mongo

import me.ely.domain.document.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-06-26
 */
@Repository
interface OrderRepository : MongoRepository<Order, String> {
}