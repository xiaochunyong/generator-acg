// package me.ely.component.security.cas
//
// import me.ely.component.security.StaffUserDetails
// import me.ely.service.feign.UserServiceFeign
// import org.slf4j.LoggerFactory
// import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken
// import org.springframework.security.core.authority.AuthorityUtils
// import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
// import org.springframework.security.core.userdetails.UserDetails
// import org.springframework.stereotype.Component
//
// @Component
// class CasAuthenticationUserDetailsService(val userService: UserServiceFeign) : AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {
//
//     val logger = LoggerFactory.getLogger(this.javaClass)
//
//     override fun loadUserDetails(token: CasAssertionAuthenticationToken): UserDetails? {
//         //        val fixedAuth = arrayOf("查询手机配置", "修改手机配置", "查询机器配置", "修改机器配置")
//         val observerId = token.name.toIntOrNull() ?: -1
//         val user = userService.getUserByUserId(observerId)
//         if (user == null) {
//             logger.info("observer user ${observerId} does not exists")
//             return null
//         }
//
//         val auth = userService.getAuthByUserId(observerId) ?: listOf()
//
//
//
//         val fixedAuth = arrayOf("_自定义_")
//         return StaffUserDetails(
//                 user.id,
//                 user.name,
//                 user.password,
//                 true,
//                 true,
//                 true,
//                 true,
//                 AuthorityUtils.createAuthorityList(*fixedAuth, *auth.map { it.name }.toTypedArray())
//         )
//     }
//
// }