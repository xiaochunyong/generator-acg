package me.ely.controller


import me.ely.domain.cons.ResponseCode
import me.ely.domain.entity.User
import me.ely.domain.request.UserSearchRequestModel
import me.ely.domain.request.UserUpdateRequestModel
import me.ely.domain.response.ItemResponse
import me.ely.domain.response.SearchResponse
import me.ely.domain.response.SimpleResponse
import me.ely.extension.merge
import me.ely.extension.new
import me.ely.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.ely.domain.cons.Authority
import me.ely.domain.request.UserCreateRequestModel
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal

/**
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-05
 */
@Api(tags=["User Module"])
@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    
    @GetMapping("/")
    @PreAuthorize("permitAll()")
    @ApiOperation(nickname = "SearchUser", value = "List User")
    fun list(model: UserSearchRequestModel, principal: Principal): SearchResponse<User> {
        logger.info("search user params: $model")
        val result = userService.search(model)
        return SearchResponse(result.content, result.pageable.pageNumber, result.pageable.pageSize, result.totalElements)
    }
    
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('" + Authority.USER_MANAGER + "')")
    @ApiOperation(nickname = "GetUser", value = "User Detail")
    fun get(@PathVariable id: Long): ItemResponse<User> {
        logger.info("get user params: $id")
        val opt = userService.findById(id)
        if (opt.isPresent) {
            return ItemResponse(opt.get())
        }
        return ResponseCode.NOT_FOUND.toResponse()
    }
    
    @PostMapping("/")
    @PreAuthorize("hasAuthority('" + Authority.USER_MANAGER + "')")
    @ApiOperation(nickname = "CreateUser", value = "Create User")
    fun create(@RequestBody model: UserCreateRequestModel): SimpleResponse {
        logger.info("create user params: $model")
        userService.save(new(model))
        return ResponseCode.SUCCESS.toResponse()
    }
    
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('" + Authority.USER_MANAGER + "')")
    @ApiOperation(nickname = "UpdateUser", value = "Update User")
    fun update(@PathVariable id: Long, @RequestBody model: UserUpdateRequestModel): SimpleResponse {
        logger.info("update user params: $id - $model")
        val opt = userService.findById(id)
        if (opt.isPresent) {
            val item = opt.get()
            userService.save(item.merge(model))
            return ResponseCode.SUCCESS.toResponse()
        }
        return ResponseCode.NOT_FOUND.toResponse()
    }
    
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('" + Authority.USER_MANAGER + "')")
    @ApiOperation(nickname = "DeleteUser", value = "Delete User")
    fun delete(@PathVariable id: Long): SimpleResponse {
        logger.info("delete user params: $id")
        userService.delete(id)
        return ResponseCode.SUCCESS.toResponse()
    }

}