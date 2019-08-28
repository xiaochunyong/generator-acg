package me.ely.controller.api

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-19
 */
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.ely.client.github.BranchClient
import me.ely.domain.response.ItemResponse
import org.slf4j.LoggerFactory
import org.springframework.util.AntPathMatcher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.HandlerMapping
import javax.servlet.http.HttpServletRequest


@Api(tags=["Github Api 模块"])
@RestController
@RequestMapping("/api/github")
class GithubApiController(val branchClient: BranchClient) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/**/**")
    @ApiOperation(nickname = "Github", value = "Github Api")
    fun branches(request: HttpServletRequest): ItemResponse<List<Map<String, Any>>> {
        val path = request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE) as String
        // bestMatchPattern='/**/**'
        val bestMatchPattern = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE) as String
        // returns:EN7800GTX/2DHTV/256M
        val repoId = AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path)

        return ItemResponse(branchClient.branches(repoId))
    }

}