package me.ely.component

import me.ely.util.HttpServletRequestExtensions
import org.apache.logging.log4j.util.Strings
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.util.StopWatch
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Pattern
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
class SimpleRequestLoggingFilter : OncePerRequestFilter() {
    companion object {

        private val VISIBLE_TYPES = listOf(
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_XML,
                MediaType.valueOf("application/*+json"),
                MediaType.valueOf("application/*+xml")
        )
        private val INVISIBLE_URL_REGEXES = listOf(
                ".*/(css|file|fonts|images|js)/.*",
                ".*\\.ico"
        )
        private val SUPPORTED_METHODS = listOf(
                HttpMethod.GET,
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.PATCH,
                HttpMethod.DELETE).map { it.name }
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        if (isAsyncDispatch(request) || isIgnore(request)) {
            filterChain.doFilter(request, response)
        } else {
            doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain)
        }
    }

    private fun doFilterWrapped(wrapRequest: ContentCachingRequestWrapper, wrapResponse: ContentCachingResponseWrapper, filterChain: FilterChain) {
        val sw = StopWatch()
        sw.start()
        var message = Strings.EMPTY
        val sessionSb = StringBuilder()
        try {
            if (wrapRequest.session != null) {
                sessionSb.append("session:\n")
                if (wrapRequest.session != null) {
                    sessionSb.append("\tsessionId: ").append(wrapRequest.session.id)
                }
                sessionSb.append("\n")
            }
            filterChain.doFilter(wrapRequest, wrapResponse)
        } finally {
            if (logger.isInfoEnabled) {
                message += getRequestMessage(wrapRequest)
                message += sessionSb.toString()
                message += getResponseMessage(wrapResponse)
                sw.stop()
                logger.info("Request Log:\nConnection Id: ${HttpServletRequestExtensions.getConnectionId(wrapRequest)}\n${message}${buildDelimiter("elapse")}${sw}")
            }
            wrapResponse.copyBodyToResponse()
        }
    }

    private fun getRequestMessage(request: ContentCachingRequestWrapper): String {
        val sb = StringBuilder()
        sb.append(buildDelimiter("request"))
        var url = request.requestURL.toString()
        if (request.queryString != null) {
            url += "?" + request.queryString
        }
        sb.append("url: $url\nip: ${request.remoteAddr}\nmethod: ${request.method}\n")
        /*headers*/
        if (request.headerNames.hasMoreElements()) {
            sb.append("headers:\n")
            Collections.list(request.headerNames).forEach { t ->
                sb.append("\t$t: ${request.getHeaders(t).toList().joinToString("; ")}\n")
            }
        }
        /*body*/
        val content = request.contentAsByteArray
        if (content.isNotEmpty()) {
            val body = getContentString(content, request.contentType, request.characterEncoding)
            sb.append("body:\n$body\n")
        }
        return sb.toString()
    }

    private fun getResponseMessage(response: ContentCachingResponseWrapper): String {
        val sb = StringBuilder()
        sb.append(buildDelimiter("response"))
        sb.append("status: ${response.status}\n")
        /*headers*/
        sb.append("headers:\n")
        if (!response.headerNames.isEmpty()) {
            response.headerNames.forEach { t -> sb.append("\t${t}: ${response.getHeaders(t).joinToString("; ")}\n") }
        }
        sb.append("\tContent-Type: ${response.contentType}\n")
        /*body*/
        val content = response.contentAsByteArray
        if (content.isNotEmpty()) {
            val body = getContentString(content, response.contentType, response.characterEncoding)
            sb.append("body:\n$body\n")
        }
        return sb.toString()
    }

    private fun getContentString(content: ByteArray, contentType: String?, contentEncoding: String): String {
        var visible = false
        if (contentType != null) {
            val mediaType = MediaType.valueOf(contentType)
            visible = VISIBLE_TYPES.any { visibleType -> visibleType.includes(mediaType) }
        }
        if (visible)
            try {
                return String(content, Charset.forName(contentEncoding))
            } catch (ignored: UnsupportedEncodingException) {

            }

        return "[${content.size} bytes content]"
    }

    private fun buildDelimiter(title: String): String {
        val len = title.length
        val leftLen = (50 - len) / 2

        return title.padStart(leftLen + len, '-').padEnd(50, '-') + "\n"
    }

    private fun isIgnore(request: HttpServletRequest): Boolean {
        return INVISIBLE_URL_REGEXES.any { Pattern.matches(it, request.requestURI) } || SUPPORTED_METHODS.none { request.method.toUpperCase() == it }

    }

    private fun wrapRequest(request: HttpServletRequest): ContentCachingRequestWrapper {
        return request as? ContentCachingRequestWrapper ?: ContentCachingRequestWrapper(request)
    }

    private fun wrapResponse(response: HttpServletResponse): ContentCachingResponseWrapper {
        return response as? ContentCachingResponseWrapper ?: ContentCachingResponseWrapper(response)
    }

}