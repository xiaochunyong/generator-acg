// package me.ely.component.security.cas
//
// import me.ely.component.security.cas.CasAuthenticationEntryPointCustomize
// import org.jasig.cas.client.session.SingleSignOutFilter
// import org.jasig.cas.client.session.SingleSignOutHttpSessionListener
// import org.jasig.cas.client.validation.Cas30ServiceTicketValidator
// import org.jasig.cas.client.validation.TicketValidator
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
// import org.springframework.context.annotation.Bean
// import org.springframework.context.annotation.Configuration
// import org.springframework.context.annotation.Primary
// import org.springframework.context.event.EventListener
// import org.springframework.security.cas.ServiceProperties
// import org.springframework.security.cas.authentication.CasAuthenticationProvider
// import org.springframework.security.web.AuthenticationEntryPoint
// import org.springframework.security.web.authentication.logout.LogoutFilter
// import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
// import javax.servlet.http.HttpSessionEvent
//
// @Configuration
// @ConditionalOnProperty(name = ["security.cas.enable"], havingValue = "true")
// class CasSecurityConfig(val casConfig: CasConfig) {
//
//     val casServerUrl: String = casConfig.casServerUrl
//     val currentServerUrl: String = casConfig.serverUrl
//
//     @Bean
//     fun serviceProperties(): ServiceProperties {
//         val serviceProperties = ServiceProperties()
//         //本机服务，访问/login/cas时进行校验登录
//         serviceProperties.service = "$currentServerUrl/login/cas"
//         serviceProperties.isSendRenew = false
//         return serviceProperties
//     }
//
//     @Bean
//     @Primary
//     fun authenticationEntryPoint(sP: ServiceProperties): AuthenticationEntryPoint {
//         val entryPoint = CasAuthenticationEntryPointCustomize()
//         //cas登录服务
//         entryPoint.loginUrl = "$casServerUrl/login"
//         entryPoint.serviceProperties = sP
//         return entryPoint
//     }
//
//     @Bean
//     fun ticketValidator(): TicketValidator {
//         //指定cas校验器
//         return Cas30ServiceTicketValidator(casServerUrl)
//     }
//
//     //cas认证
//     @Bean
//     fun casAuthenticationProvider(casAuthenticationUserDetailsService: CasAuthenticationUserDetailsService): CasAuthenticationProvider {
//         val provider = CasAuthenticationProvider()
//         provider.setServiceProperties(serviceProperties())
//         provider.setTicketValidator(ticketValidator())
//         provider.setAuthenticationUserDetailsService(casAuthenticationUserDetailsService)
//         provider.setKey("CasAuthenticationProviderKey")
//         return provider
//     }
//
//
//     @Bean
//     fun securityContextLogoutHandler(): SecurityContextLogoutHandler {
//         return SecurityContextLogoutHandler()
//     }
//
//     @Bean
//     fun logoutFilter(): LogoutFilter {
//         //退出后转发路径
//         val logoutSuccessUrl = "$casServerUrl/logout?service=${casConfig.clientUrl}"
//         val logoutFilter = LogoutFilter(CasLogoutSuccessHandler(logoutSuccessUrl), securityContextLogoutHandler())
//         //cas退出
//         logoutFilter.setFilterProcessesUrl("/logout/cas")
//         return logoutFilter
//     }
//
//     @Bean
//     fun singleSignOutFilter(): SingleSignOutFilter {
//         //单点退出
//         val singleSignOutFilter = SingleSignOutFilter()
//         singleSignOutFilter.setCasServerUrlPrefix(casServerUrl)
//         singleSignOutFilter.setIgnoreInitConfiguration(true)
//         return singleSignOutFilter
//     }
//
//     //设置退出监听
//     @EventListener
//     fun singleSignOutHttpSessionListener(
//             event: HttpSessionEvent): SingleSignOutHttpSessionListener {
//         return SingleSignOutHttpSessionListener()
//     }
// }