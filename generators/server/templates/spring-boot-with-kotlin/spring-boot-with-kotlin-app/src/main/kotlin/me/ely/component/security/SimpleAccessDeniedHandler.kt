package me.ely.component.security

import me.ely.domain.cons.ResponseCode
import me.ely.domain.response.SimpleResponse
import me.ely.util.HttpServletRequestExtensions
import me.ely.util.json.JSON
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import java.io.IOException
import java.net.URLEncoder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SimpleAccessDeniedHandler : AccessDeniedHandler {

    @Throws(IOException::class)
    override fun handle(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, e: AccessDeniedException) {
        val errorMessage = String.format("缺少权限【%s】，如已分配请重新登录，若再次出现请联系管理员!", HttpServletRequestExtensions.getRequestAuthority())
        // if (HttpServletRequestExtensions.isAjax(httpServletRequest)) {
            httpServletResponse.status = HttpStatus.OK.value()
            httpServletResponse.contentType = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
            httpServletResponse.characterEncoding = "UTF-8"
            httpServletResponse.writer.write(JSON.stringify(SimpleResponse(ResponseCode.UnAuthorized.value, errorMessage)))
        // } else {
        //     var unauthorizedUri = "/403"
        //     unauthorizedUri += "?error=" + URLEncoder.encode(HttpServletRequestExtensions.getRequestAuthority() + errorMessage, "utf-8")
        //     httpServletResponse.sendRedirect(unauthorizedUri)
        // }
    }

}
