package dev.rennen.webapp.service.impl;

import cn.hutool.core.lang.UUID;
import dev.rennen.webapp.common.constants.CommonConstant;
import dev.rennen.webapp.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(MultipartFile file, String pathPrefix) {
        String fileName = UUID.fastUUID() + CommonConstant.DEFAULT_IMAGE_FORMAT;
        if (file.isEmpty()) {
            return null;
        }

        try {
            // 获取文件名并创建保存路径
            Path fullPath = Paths.get(pathPrefix, fileName);

            // 确保保存路径存在
            Files.createDirectories(fullPath.getParent());

            // 保存文件到本地
            Files.write(fullPath, file.getBytes());

            return fileName;
        } catch (IOException e) {
            log.error("Error occurred while uploading file: {}", e.getMessage());
            return null;
        }

    }
}
