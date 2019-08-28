package me.ely.controller

import io.swagger.annotations.ApiOperation
import me.ely.domain.cons.ResponseCode
import me.ely.domain.entity.User
import me.ely.domain.response.SimpleResponse
import me.ely.util.json.JSON
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-09
 */
class CodeSnippetsController {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("upload")
    @ApiOperation(nickname = "uploadFile", value = "上传文件")
    fun uploadFile(@RequestParam("file") file: MultipartFile): SimpleResponse {
        if (file.originalFilename == null || !file.originalFilename?.endsWith("xml")!!) {
            return ResponseCode.BAD_REQUEST.toResponse("xml")
        }

        try {
            val phone = JSON.parse<User>(String(file.inputStream.readBytes()))
            return ResponseCode.SUCCESS.toResponse()
        } catch (e: IOException) {
            logger.error(e.toString(), e)
        }
        return ResponseCode.INTERNAL_SERVER_ERROR.toResponse("上传失败")
    }

}