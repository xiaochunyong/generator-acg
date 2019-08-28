package me.ely.client.github

import me.ely.component.FeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 * feign config @see https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-19
 */
@Configuration
@FeignClient(name = "githubBranchClient", url=("\${github.endpoint}"), configuration = [FeignConfig::class])
interface BranchClient {

    @GetMapping("/repos/{repo}/branches")
    fun branches(@PathVariable repo: String): List<Map<String, Any>>

}