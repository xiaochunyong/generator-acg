package me.ely.repository.jpa

import me.ely.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-05
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
}