// package me.ely.component.security.cas
//
// import org.springframework.beans.factory.annotation.Value
// import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
// import org.springframework.context.annotation.Configuration
//
// @Configuration
// @ConditionalOnProperty(name = ["security.cas.enable"], havingValue = "true")
// class CasConfig {
//
//     @Value("\${cas-server.url}")
//     lateinit var casServerUrl: String
//     @Value("\${inspection.client-url}")
//     lateinit var clientUrl: String
//     @Value("\${inspection.server-url}")
//     lateinit var serverUrl: String
//
// }