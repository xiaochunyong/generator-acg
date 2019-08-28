package me.ely.component

import com.fasterxml.jackson.databind.ObjectMapper
import feign.Logger
import me.ely.util.json.JacksonJson
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        JacksonJson.initObjectMapper(objectMapper)
        return objectMapper
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }

}