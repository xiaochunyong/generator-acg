package me.ely.component

import com.google.common.base.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class Swagger2Config {

    @Bean
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(createApiInfo())
                .select()
                .apis(Predicates.or(
                        RequestHandlerSelectors.basePackage("me.ely.controller")
                ))
                .paths(PathSelectors.any())
                .build()
    }

    private fun createApiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("spring-boot-with-kotlin接口说明文档")
                .description("spring-boot-with-kotlin接口说明文档")
                .version("0.1")
                .build()
    }
}