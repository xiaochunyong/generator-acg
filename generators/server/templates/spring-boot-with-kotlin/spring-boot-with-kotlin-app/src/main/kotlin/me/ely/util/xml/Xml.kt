// package me.ely.util.xml
//
// import com.fasterxml.jackson.annotation.JsonInclude
// import com.fasterxml.jackson.databind.DeserializationFeature
// import com.fasterxml.jackson.dataformat.xml.XmlMapper
// import com.fasterxml.jackson.module.kotlin.KotlinModule
// import java.text.SimpleDateFormat
// import java.util.*
//
// /**
//  *
//  *
//  * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
//  * @see
//  * @since   2019-08-08
//  */
// object Xml {
//
//     val xmlMapper = XmlMapper()
//
//     fun initObjectMapper(xmlMapper: XmlMapper) {
//         xmlMapper.registerModule(KotlinModule())
//         xmlMapper.dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//         xmlMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"))
//         xmlMapper.setDefaultPropertyInclusion(JsonInclude.Include.ALWAYS)
//         xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
//     }
//     init {
//         initObjectMapper(xmlMapper)
//     }
//
//     fun stringify(o: Any): String {
//         return xmlMapper.writeValueAsString(o)
//     }
//
//     final inline fun <reified T> parse(xml: String?): T {
//         return xmlMapper.readValue(xml, T::class.java)
//     }
// }