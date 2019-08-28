package me.ely.util

import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

object HttpServletRequestExtensions {
    private const val CONNECTION_ID_KEY = "ConnectionId"

    fun getConnectionId(request: HttpServletRequest): String {
        var id: Any? = request.getAttribute(CONNECTION_ID_KEY)
        if (id == null) {
            id = UUID.randomUUID().toString()
            request.setAttribute(CONNECTION_ID_KEY, id)
        }
        return id as String
    }

    private fun getHttpSession(): HttpSession {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        return requestAttributes?.request!!.session
    }

    fun getConnectionId(): String {
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
        return getConnectionId(requestAttributes?.request!!)
    }

    fun isAjax(request: HttpServletRequest): Boolean {
        val requestedWithHeader = request.getHeader("X-Requested-With")
        return "XMLHttpRequest" == requestedWithHeader
    }

    private val connectionIdKey = "ConnectionId"
    private val requestAuthority = "RequestAuthority"

    fun saveRequestAuthority(authority: String) {
        Objects.requireNonNull<RequestAttributes>(RequestContextHolder.getRequestAttributes()).setAttribute(requestAuthority, authority, RequestAttributes.SCOPE_SESSION)
    }

    fun getRequestAuthority(): String? {
        return Objects.requireNonNull<RequestAttributes>(RequestContextHolder.getRequestAttributes()).getAttribute(requestAuthority, RequestAttributes.SCOPE_SESSION) as String?
    }

    fun getConnectionIdWithPrefix(): String {
        return "ConnectionId: ${getConnectionId()}; "
    }


    fun removeSessionByKey(key: String) {
        getHttpSession().removeAttribute(key)
    }

    fun <T> getSessionByKey(key: String): T? {
        return getHttpSession().getAttribute(key) as? T
    }

    fun setSession(key: String, value: Any?) {
        getHttpSession().setAttribute(key, value)
    }

}