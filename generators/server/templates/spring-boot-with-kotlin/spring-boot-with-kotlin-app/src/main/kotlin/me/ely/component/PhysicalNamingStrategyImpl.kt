package me.ely.component

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import java.io.Serializable
import java.util.*


class PhysicalNamingStrategyImpl : PhysicalNamingStrategyStandardImpl(), Serializable {

    override fun toPhysicalTableName(name: Identifier, context: JdbcEnvironment?): Identifier {
        return Identifier(addUnderscores(name.text), name.isQuoted)
    }

    override fun toPhysicalColumnName(name: Identifier, context: JdbcEnvironment?): Identifier {
        return Identifier(addUnderscores(name.text), name.isQuoted)
    }

    companion object {

        val INSTANCE = PhysicalNamingStrategyImpl()


        protected fun addUnderscores(name: String): String {
            val buf = StringBuilder(name.replace('.', '_'))
            var i = 1
            while (i < buf.length - 1) {
                if (Character.isLowerCase(buf[i - 1]) &&
                        Character.isUpperCase(buf[i]) &&
                        Character.isLowerCase(buf[i + 1])) {
                    buf.insert(i++, '_')
                }
                i++
            }
            return buf.toString().toLowerCase(Locale.ROOT)
        }
    }
}