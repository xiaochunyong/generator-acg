// package me.ely.component
//
// import me.ely.util.HttpServletRequestExtensions
// import org.springframework.context.annotation.Configuration
// import org.springframework.core.Ordered
// import org.springframework.core.annotation.Order
// import org.springframework.web.filter.OncePerRequestFilter
// import javax.servlet.FilterChain
// import javax.servlet.http.HttpServletRequest
// import javax.servlet.http.HttpServletResponse
//
// @Order(Ordered.HIGHEST_PRECEDENCE + 1)
// @Configuration
// class SimpleExceptionHandler : OncePerRequestFilter() {
//     override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
//         try {
//             filterChain.doFilter(request, response)
//         } catch (e: Exception) {
//             logger.error("Unhandled exception, Connection Id:${HttpServletRequestExtensions.getConnectionId(request)}", e)
//             throw e
//         }
//     }
// }