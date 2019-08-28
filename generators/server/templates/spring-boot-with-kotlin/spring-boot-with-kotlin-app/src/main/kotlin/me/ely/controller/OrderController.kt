package me.ely.controller


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
@RequestMapping("/order")
class OrderController(val orderService: OrderService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    @ApiOperation(nickname = "SearchOrder", value = "List Order")
    fun list(model: OrderSearchRequestModel): SearchResponse<Order> {
        logger.info("search order params: $model")
        val result = orderService.search(model)
        return SearchResponse(result.content, result.pageable.pageNumber, result.pageable.pageSize, result.totalElements)
    }

    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    @ApiOperation(nickname = "GetOrder", value = "Order Detail")
    fun get(@PathVariable id: String): ItemResponse<Order> {
        logger.info("get order params: $id")
        val opt = orderService.findById(id)
        if (opt.isPresent) {
            return ItemResponse(opt.get())
        }
        return ResponseCode.NOT_FOUND.toResponse()
    }

    @PostMapping("/")
    @PreAuthorize("permitAll()")
    @ApiOperation(nickname = "CreateOrder", value = "Create Order")
    fun create(@RequestBody model: OrderCreateRequestModel): SimpleResponse {
        logger.info("create order params: $model")
        orderService.save(new(model))
        return ResponseCode.SUCCESS.toResponse()
    }

    @PutMapping("{id}")
    @PreAuthorize("permitAll()")
    @ApiOperation(nickname = "UpdateOrder", value = "Update Order")
    fun update(@PathVariable id: String, @RequestBody model: OrderUpdateRequestModel): SimpleResponse {
        logger.info("update order params: $id - $model")
        val opt = orderService.findById(id)
        if (opt.isPresent) {
            val item = opt.get()
            orderService.save(item.merge(model))
            return ResponseCode.SUCCESS.toResponse()
        }
        return ResponseCode.NOT_FOUND.toResponse()
    }

    @DeleteMapping("{id}")
    @PreAuthorize("permitAll()")
    @ApiOperation(nickname = "DeleteOrder", value = "Delete Order")
    fun delete(@PathVariable id: String): SimpleResponse {
        logger.info("delete order params: $id")
        orderService.delete(id)
        return ResponseCode.SUCCESS.toResponse()
    }

}