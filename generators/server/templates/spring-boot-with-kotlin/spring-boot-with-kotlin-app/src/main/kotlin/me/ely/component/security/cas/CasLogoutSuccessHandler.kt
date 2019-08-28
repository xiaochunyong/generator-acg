// package me.ely.component.security.cas
//
// import me.ely.util.HttpServletRequestExtensions
// import org.springframework.security.core.Authentication
// import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
// import javax.servlet.http.HttpServletRequest
// import javax.servlet.http.HttpServletResponse
//
// class CasLogoutSuccessHandler(val logoutSuccessUrl: String) : LogoutSuccessHandler {
//
//     override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
//         if (HttpServletRequestExtensions.isAjax(request)) {
//             response.addHeader("Access-Control-Allow-Origin", "http://localhost:4444")
//             response.addHeader("Access-Control-Allow-Methods", "*")
//             response.addHeader("Access-Control-Allow-Headers", "x-requested-with")
//             response.addHeader("Access-Control-Allow-Credentials", "true")
//             response.status = 200
//             response.contentType = "application/json"
//             response.writer.write("""{ "code": 302, "message": "to cas logout", "data": "$logoutSuccessUrl"}""")
//             response.writer.flush()
//             response.writer.close()
//         } else {
//             response.sendRedirect(logoutSuccessUrl)
//         }
//     }
//
//
// }