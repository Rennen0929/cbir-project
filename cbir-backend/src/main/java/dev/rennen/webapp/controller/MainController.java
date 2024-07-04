package dev.rennen.webapp.controller;

import dev.rennen.webapp.common.constants.CommonConstant;
import dev.rennen.webapp.dto.MatchingResultResponseVo;
import dev.rennen.webapp.service.FileService;
import dev.rennen.webapp.service.ImageService;
import dev.rennen.webapp.dto.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 夏嘉诚
 */
@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MainController {

    @Resource
    FileService fileService;

    @Resource
    ImageService imageService;

    /**
     * 根据颜色匹配
     * @param file 上传的图片
     * @return 匹配结果
     */
    @PostMapping("/calculateColor")
    public Result<List<MatchingResultResponseVo>> calculateColor(MultipartFile file) {
        // 1. 先将从接口上传的文件保存到本地
        String filename = fileService.uploadFile(file, CommonConstant.IMAGE_UPLOAD_PREFIX);
        if (filename == null) {
            return Result.error(500, "上传文件失败");
        }
        return Result.success(imageService.matchImagesByColor("\\" + filename, CommonConstant.IMAGE_UPLOAD_PREFIX));
    }

    /**
     * 根据纹理匹配图片
     */
    @PostMapping("/calculateTexture")
    public Result<List<MatchingResultResponseVo>> calculateTexture(MultipartFile file) {
        // 1. 先将从接口上传的文件保存到本地
        String filename = fileService.uploadFile(file, CommonConstant.IMAGE_UPLOAD_PREFIX);
        if (filename == null) {
            return Result.error(500, "上传文件失败");
        }
        return Result.success(imageService.matchImagesByTexture("\\" + filename, CommonConstant.IMAGE_UPLOAD_PREFIX));
    }


}
