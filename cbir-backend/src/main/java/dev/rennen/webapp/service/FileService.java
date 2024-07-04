package dev.rennen.webapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadFile(MultipartFile file, String pathPrefix);
}
