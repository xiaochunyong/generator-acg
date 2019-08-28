package me.ely.util.json

import me.ely.domain.dto.UserDto
import org.junit.Assert.*
import org.junit.Test

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-06-27
 */
class JSONTest {

    @Test
    fun test() {
        val model = JSON.parse<UserDto>("""{ "id": 1, "name": "Ely", "height": 200.0, "birthday": "2019-05-13 03:42:57" }""")
        assertEquals(model.id, 1)
    }

}