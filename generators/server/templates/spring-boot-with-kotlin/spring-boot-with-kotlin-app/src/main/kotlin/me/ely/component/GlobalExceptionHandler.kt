package me.ely.component

import me.ely.controller.ConstantController
import me.ely.domain.response.SimpleResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-07-16
 */
@ControllerAdvice(basePackageClasses = [ConstantController::class])
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    /**
     * 不在父类中定义的拦截列表, 如果需要拦截, 在此处拦截. 由于是Kotlin语言, 所以必须在函数签名上明确表示抛出的异常(通过@Throws(Exception::class))
     */
    @ExceptionHandler(Exception::class)
    fun handleOtherException(e: Exception): ResponseEntity<Any> {
        return ResponseEntity(SimpleResponse(400, e.message ?: ""), HttpStatus.BAD_REQUEST)
    }

    /**
     * 父类定义了异常拦截列表
     */
    override fun handleExceptionInternal(ex: java.lang.Exception, body: Any?, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(SimpleResponse(status.value(), ex.message ?: ""), status)
    }

}