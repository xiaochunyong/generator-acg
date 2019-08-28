package me.ely.component.security.jdbc

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-05
 */
class LoginFailureHandler : AuthenticationFailureHandler {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun onAuthenticationFailure(request: HttpServletRequest, response: HttpServletResponse, e: AuthenticationException) {
        logger.error(e.message, e)
        response.sendRedirect("/login?message=${e.message}")
    }

}