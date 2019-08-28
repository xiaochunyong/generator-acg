package me.ely.service

import me.ely.domain.document.Order
import me.ely.domain.request.OrderSearchRequestModel
import me.ely.repository.mongo.OrderRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class OrderService : AbstractMongoService<Order, String, OrderRepository>() {

    fun search(requestModel: OrderSearchRequestModel): Page<Order> {
        val query = buildSearchQuery(requestModel)
        val sort = Sort(Sort.Direction.DESC, Order::id.name)
        val pageableAndSort = PageRequest.of(requestModel.pageIndex, requestModel.pageSize, sort)
        val count = mongoTemplate.count(query, Order::class.java)
        val data = mongoTemplate.find(query.with(pageableAndSort), Order::class.java)
        return PageImpl(data, pageableAndSort, count)
    }

    private fun buildSearchQuery(requestModel: OrderSearchRequestModel): Query {
        val query = Query()
        if (!requestModel.no.isNullOrEmpty()) {
            query.addCriteria(Criteria.where("no").regex(".*?" + requestModel.no + ".*"))
        }
        if (!requestModel.productName.isNullOrEmpty()) {
            query.addCriteria(Criteria.where("productName").regex(".*?" + requestModel.productName + ".*"))
        }
        return query
    }
}