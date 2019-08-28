// package me.ely.component.security.cas
//
// import me.ely.util.HttpServletRequestExtensions
// import org.jasig.cas.client.util.CommonUtils
// import org.springframework.beans.factory.InitializingBean
// import org.springframework.security.cas.ServiceProperties
// import org.springframework.security.core.AuthenticationException
// import org.springframework.security.web.AuthenticationEntryPoint
// import org.springframework.util.Assert
// import java.io.IOException
// import javax.servlet.ServletException
// import javax.servlet.http.HttpServletRequest
// import javax.servlet.http.HttpServletResponse
//
// open class CasAuthenticationEntryPointCustomize : AuthenticationEntryPoint, InitializingBean {
//
//     // ~ Instance fields
//     // ================================================================================================
//     var serviceProperties: ServiceProperties? = null
//
//     /**
//      * The enterprise-wide CAS login URL. Usually something like
//      * `https://www.mycompany.com/cas/login`.
//      *
//      * @return the enterprise-wide CAS login URL
//      */
//     var loginUrl: String? = null
//
//     /**
//      * Determines whether the Service URL should include the session id for the specific
//      * user. As of CAS 3.0.5, the session id will automatically be stripped. However,
//      * older versions of CAS (i.e. CAS 2), do not automatically strip the session
//      * identifier (this is a bug on the part of the older server implementations), so an
//      * option to disable the session encoding is provided for backwards compatibility.
//      *
//      * By default, encoding is enabled.
//      */
//     /**
//      * Sets whether to encode the service url with the session id or not.
//      * @return whether to encode the service url with the session id or not.
//      */
//     /**
//      * Sets whether to encode the service url with the session id or not.
//      *
//      * @param encodeServiceUrlWithSessionId whether to encode the service url with the
//      * session id or not.
//      */
//     protected var encodeServiceUrlWithSessionId = true
//         set
//
//     // ~ Methods
//     // ========================================================================================================
//
//     @Throws(Exception::class)
//     override fun afterPropertiesSet() {
//         Assert.hasLength(this.loginUrl, "loginUrl must be specified")
//         Assert.notNull(this.serviceProperties, "serviceProperties must be specified")
//         Assert.notNull(this.serviceProperties!!.service,
//                 "serviceProperties.getService() cannot be null.")
//     }
//
//     @Throws(IOException::class, ServletException::class)
//     override fun commence(servletRequest: HttpServletRequest,
//                           response: HttpServletResponse,
//                           authenticationException: AuthenticationException) {
//
//         val urlEncodedService = createServiceUrl(servletRequest, response)
//         val redirectUrl = createRedirectUrl(urlEncodedService)
//
//         preCommence(servletRequest, response)
//
//         if (HttpServletRequestExtensions.isAjax(servletRequest)) {
//             response.addHeader("Access-Control-Allow-Origin", "http://localhost:4444")
//             response.addHeader("Access-Control-Allow-Methods", "*")
//             response.addHeader("Access-Control-Allow-Headers", "x-requested-with,*")
//             response.addHeader("Access-Control-Allow-Credentials", "true")
//             response.status = 200
//             response.contentType = "application/json"
//             response.writer.write("""{ "code": 302, "message": "to auth", "data": "$redirectUrl"}""")
//             response.writer.flush()
//             response.writer.close()
//         } else {
//             response.sendRedirect(redirectUrl)
//         }
//
//     }
//
//     /**
//      * Constructs a new Service Url. The default implementation relies on the CAS client
//      * to do the bulk of the work.
//      * @param request the HttpServletRequest
//      * @param response the HttpServlet Response
//      * @return the constructed service url. CANNOT be NULL.
//      */
//     protected fun createServiceUrl(request: HttpServletRequest,
//                                    response: HttpServletResponse): String {
//         return CommonUtils.constructServiceUrl(null, response,
//                 this.serviceProperties!!.service, null,
//                 this.serviceProperties!!.artifactParameter,
//                 this.encodeServiceUrlWithSessionId)
//     }
//
//     /**
//      * Constructs the Url for Redirection to the CAS server. Default implementation relies
//      * on the CAS client to do the bulk of the work.
//      *
//      * @param serviceUrl the service url that should be included.
//      * @return the redirect url. CANNOT be NULL.
//      */
//     protected fun createRedirectUrl(serviceUrl: String): String {
//         return CommonUtils.constructRedirectUrl(this.loginUrl!!,
//                 this.serviceProperties!!.serviceParameter, serviceUrl,
//                 this.serviceProperties!!.isSendRenew, false)
//     }
//
//     /**
//      * Template method for you to do your own pre-processing before the redirect occurs.
//      *
//      * @param request the HttpServletRequest
//      * @param response the HttpServletResponse
//      */
//     protected fun preCommence(request: HttpServletRequest,
//                               response: HttpServletResponse) {
//
//     }
// }