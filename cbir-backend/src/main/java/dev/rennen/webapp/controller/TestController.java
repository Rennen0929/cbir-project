package dev.rennen.webapp.controller;

import dev.rennen.webapp.common.constants.CommonConstant;
import dev.rennen.webapp.mapper.UserMapper;
import dev.rennen.webapp.model.UserModel;
import dev.rennen.webapp.service.FileService;
import dev.rennen.webapp.dto.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 夏嘉诚
 * 该类提供一些测试接口，和数据准备接口
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {


    @Resource
    UserMapper userMapper;

    @Resource
    FileService fileService;

    @GetMapping("/hello")
    public Result<UserModel> hello() {
        log.info("测试日志输出");
        return Result.success(userMapper.selectByPrimaryKey(1L));
    }

    @PostMapping("/uploadImage")
    public Result<String> uploadImage(MultipartFile file) {
        String fileName = fileService.uploadFile(file, CommonConstant.IMAGE_UPLOAD_PREFIX);
        return Result.success(fileName);
    }

}
