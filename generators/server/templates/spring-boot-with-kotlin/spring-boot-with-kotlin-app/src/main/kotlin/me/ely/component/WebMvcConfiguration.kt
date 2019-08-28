package me.ely.component

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration : WebMvcConfigurer {

    /**
     * 同时引入了jackson 和 xml 后, 会导致部分接口返回xml(貌似不生效, 暂时注释了pom依赖)
     */
    // override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
    //     configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8)
    // }

    override fun addCorsMappings(registry: CorsRegistry) {
        println("cors")
        registry.addMapping("/**")
                .allowedOrigins(
                        "*"
                        // "http://localhost:3000",
                        // "http://localhost:4444",
                        // "http://localhost:8000"
                )
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
    }

}