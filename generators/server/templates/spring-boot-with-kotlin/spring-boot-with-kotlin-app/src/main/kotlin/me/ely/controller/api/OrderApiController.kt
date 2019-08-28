package me.ely.controller.api


import me.ely.domain.cons.ResponseCode
import me.ely.domain.document.Order
import me.ely.domain.request.OrderCreateRequestModel
import me.ely.domain.request.OrderSearchRequestModel
import me.ely.domain.request.OrderUpdateRequestModel
import me.ely.domain.response.ItemResponse
import me.ely.domain.response.SearchResponse
import me.ely.domain.response.SimpleResponse
import me.ely.extension.merge
import me.ely.extension.new
import me.ely.service.OrderService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-13
 */
@Api(tags=["Order Module"])
@RestController
@RequestMapping("/api/order")
class OrderApiController(val orderService: OrderService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/")
    @ApiOperation(nickname = "SearchOrderByGateway", value = "List Order")
    fun list(model: OrderSearchRequestModel): SearchResponse<Order> {
        logger.info("search order params: $model")
        val result = orderService.search(model)
        return SearchResponse(result.content, result.pageable.pageNumber, result.pageable.pageSize, result.totalElements)
    }

}