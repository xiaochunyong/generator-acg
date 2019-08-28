package me.ely.component.security

import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.DigestUtils

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-05
 */
class MD5PasswordEncoder : PasswordEncoder {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * 对密码进行加密
     */
    override fun encode(charSequence: CharSequence): String {
        logger.info("PasswordEncoder.encode: ${charSequence}")
        return DigestUtils.md5DigestAsHex(charSequence.toString().toByteArray())
    }

    /**
     * 密码校验
     */
    override fun matches(charSequence: CharSequence, s: String): Boolean {
        val encode = DigestUtils.md5DigestAsHex(charSequence.toString().toByteArray())
        return encode == s
    }

}