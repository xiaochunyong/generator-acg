// package me.ely.component.security.cas
//
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
// import org.springframework.security.core.Authentication
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler
// import org.springframework.stereotype.Component
// import javax.servlet.http.HttpServletRequest
// import javax.servlet.http.HttpServletResponse
//
// @Component
// @ConditionalOnProperty(name = ["security.cas.enable"], havingValue = "true")
// class CasLoginSuccessHandler(val casConfig: CasConfig) : AuthenticationSuccessHandler {
//
//     override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
//         response.sendRedirect(casConfig.clientUrl)
//     }
// }