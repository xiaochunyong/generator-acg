package me.ely.extension

import java.beans.Introspector
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-02
 */

/**
 * 创建对象
 */
inline fun <reified T: Any> new(source: Any? = null): T {
    val obj = T::class.java.getDeclaredConstructor().newInstance()
    if (source == null) {
        return obj
    }
    return obj.merge(source)
}


/**
 * 实现BeanUtils.copy
 */
inline infix fun <reified T : Any, reified S : Any> T.merge(source: S): T {
    val beanInfo = Introspector.getBeanInfo(source.javaClass)
    val nameToPropertyOfSource = beanInfo.propertyDescriptors.associateBy { it.name }
    val nameToPropertyOfTarget = T::class.memberProperties.associateBy { it.name }

    // 根据目标类的主构造函数创建对象, 不在主构造函数中的var属性在下一步处理, 不在主构造函数中的val属性会略过.
    val primaryConstructor = T::class.primaryConstructor!!
    val primaryConstructorParams = mutableListOf<String>()
    val args = primaryConstructor.parameters.associate { parameter ->
        val name = parameter.name!!
        primaryConstructorParams.add(name)
        val readMethodOfSource = nameToPropertyOfSource[name]?.readMethod
        val propertyOfTarget = nameToPropertyOfTarget[name]
        parameter to (readMethodOfSource?.invoke(source) ?: propertyOfTarget?.get(this))
    }
    val obj = primaryConstructor.callBy(args)

    // 为不在主构造函数中的字段赋值(比如继承的字段)
    nameToPropertyOfTarget.forEach { (name, propertyOfTarget) ->
        // 只处理, 没有在构造函数中出现, 并且是var修饰的属性. 没有在构造中出现的val属性无法处理
        if (!primaryConstructorParams.contains(name) && propertyOfTarget is KMutableProperty1) {
            val mutableProperty = propertyOfTarget as KMutableProperty1<T, Any?>
            val readMethodSource = nameToPropertyOfSource[name]?.readMethod
            if (readMethodSource != null) {
                mutableProperty.set(obj, readMethodSource.invoke(source))
            } else {
                try {
                    mutableProperty.set(obj, propertyOfTarget.get(this))
                } catch (e: Exception) {
                    // lateinit 属性如果没有值, 在这里会报错, 目前还没有找到判断是否有值的方式
                    // 压制异常 e.printStackTrace()
                }
            }
        }
    }
    return obj
}