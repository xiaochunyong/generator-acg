package me.ely

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling


@EnableScheduling
@EntityScan("me.ely.domain")
@EnableFeignClients(basePackages = ["me.ely.client"])
@EnableJpaRepositories("me.ely.repository.jpa")
@EnableMongoRepositories("me.ely.repository.mongo")
@SpringBootApplication(
        scanBasePackages = ["me.ely"]
        // ,exclude = [ MongoAutoConfiguration::class, MongoDataAutoConfiguration::class ]
)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}