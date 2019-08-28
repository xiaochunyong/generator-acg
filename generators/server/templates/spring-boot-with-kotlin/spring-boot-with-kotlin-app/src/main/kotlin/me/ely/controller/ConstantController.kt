package me.ely.controller

import me.ely.util.json.JSON
import org.slf4j.LoggerFactory
import org.springframework.core.io.ResourceLoader
import org.springframework.web.bind.annotation.*
import org.springframework.core.type.classreading.CachingMetadataReaderFactory
import org.springframework.core.io.support.ResourcePatternUtils


/**
 * 枚举代码生成器
 */
@RestController
@RequestMapping("/v2/cons")
class ConstantController(val resourceLoader: ResourceLoader) {

    val logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * 前端Item的结构, 可以根据使用的JS库来调整字段名, 类型
     */
    data class FrontItem(
            val value: Int,
            val label: String
    )

    /**
     * 生成JS代码
     */
    @RequestMapping("/gen", method = [RequestMethod.HEAD, RequestMethod.GET], produces = ["application/javascript;charset=UTF-8"])
    @ResponseBody
    fun generateJavaScriptCode(): String {
        val resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader)
        val metaReader = CachingMetadataReaderFactory(resourceLoader)

        // 扫描me.ely.domain.cons包及其子包下的枚举生成JS代码.
        val packageName = "me.ely.domain.cons".replace(".", "/")
        val resources = resolver.getResources("classpath*:$packageName/**/*.class")

        val sb = StringBuffer("const commonConverter = (list, value) => {if (typeof value === 'undefined') return '';const item = list.filter(it => `${'$'}{it.value}` === `${'$'}{value}`);if (item.length > 0) return item[0].label;return '';};")
        for (r in resources) {
            val reader = metaReader.getMetadataReader(r)
            if (reader.classMetadata.superClassName == "java.lang.Enum") {
                val clazz = (Class.forName(reader.classMetadata.className) as Class)
                try {
                    val valueMethod = clazz.getDeclaredMethod("getValue")
                    val labelMethod = clazz.getDeclaredMethod("getLabel")

                    val simpleClassName = reader.classMetadata.className.substring(reader.classMetadata.className.lastIndexOf(".") + 1)
                    val simpleClassNameCamel = simpleClassName.substring(0, 1).toLowerCase() + simpleClassName.substring(1)

                    clazz.enumConstants.forEach { sb.append("export const ${simpleClassName}_${it} = ${valueMethod.invoke(it)};") }
                    sb.append("export const ${simpleClassNameCamel}List = ${JSON.stringify(clazz.enumConstants.map { FrontItem(valueMethod.invoke(it) as Int, labelMethod.invoke(it) as String) })};")
                    sb.append("export const ${simpleClassNameCamel}Converter = commonConverter.bind(null, ${simpleClassNameCamel}List);")
                } catch (e: Exception) {
                    logger.error(e.message)
                }
            }

        }
        return sb.toString()
    }

}
/*  生成的JS代码类似
const commonConverter = (list, value) => {
  if (typeof value === 'undefined') return '';
  const item = list.filter(it => `${it.value}` === `${value}`);
  if (item.length > 0) return item[0].label;
  return '';
};
export const AxisSpeedLevel_One = 1;
export const AxisSpeedLevel_Two = 2;
export const AxisSpeedLevel_Three = 2;
export const AxisSpeedLevel_Four = 4;
export const axisSpeedLevelList = [
  { 'value': 1, 'label': '档位1' },
  { 'value': 2, 'label': '档位2' },
  { 'value': 2, 'label': '档位3', },
  { 'value': 4, 'label': '档位4' },
];
export const axisSpeedLevelConverter = commonConverter.bind(null, axisSpeedLevelList);
....
 */