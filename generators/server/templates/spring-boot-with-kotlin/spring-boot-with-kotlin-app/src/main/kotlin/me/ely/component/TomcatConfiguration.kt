package me.ely.component

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class TomcatConfiguration : EnvironmentAware {

    lateinit var env: Environment

    override fun setEnvironment(environment: Environment) {
        this.env = environment
    }

    @Bean
    fun webServerFactory(): ConfigurableServletWebServerFactory {
        val factory = TomcatServletWebServerFactory()
        if (!env.activeProfiles.contains("dev")) {
            println("current env is not dev")
            factory.contextPath = "/" // TODO 自定义上下文
        }
        return factory
    }
}