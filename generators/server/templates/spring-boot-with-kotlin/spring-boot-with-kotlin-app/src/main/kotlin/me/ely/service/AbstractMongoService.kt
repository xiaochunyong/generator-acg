package me.ely.service

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-06-26
 */
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*
import kotlin.collections.HashMap

abstract class AbstractMongoService<Resource, Id, Repository : MongoRepository<Resource, Id>> {

    @Autowired(required = false)
    open lateinit var repository: Repository

    @Autowired(required = false)
    open lateinit var mongoTemplate: MongoTemplate

    @Autowired
    open lateinit var appContext: ApplicationContext

    private val beanCache = HashMap<Class<*>, Any>()


    open fun findById(id: Id): Optional<Resource> {
        return this.repository.findById(id)
    }

    open fun findAllById(ids: List<Id>): MutableIterable<Resource> {
        return this.repository.findAllById(ids)
    }

    open fun save(resource: Resource): Resource {
        return this.repository.save(resource)
    }

    open fun delete(id: Id) {
        this.repository.deleteById(id)
    }

    open fun findAll(): List<Resource> {
        return this.repository.findAll()
    }

    open fun saveAll(resources: List<Resource>): List<Resource> {
        return this.repository.saveAll(resources)
    }

    open fun <T : Any> getBean(clazz: Class<T>, cache: Boolean): T {
        var service: T? = null
        if (cache) {
            val t = beanCache[clazz]
            if (t != null) {
                service = t as T
            }
        }
        if (service == null) {
            service = appContext.getBean(clazz)
            if (cache) {
                beanCache[clazz] = service
            }
        }
        return service
    }

}