package me.ely

import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-05
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@WebAppConfiguration
class SpringApplicationTest {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var ctx: WebApplicationContext

    @Before
    fun before() {
        logger.info("before test")
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx).build()
    }

    @After
    fun after() {
        logger.info("after test")
    }
}