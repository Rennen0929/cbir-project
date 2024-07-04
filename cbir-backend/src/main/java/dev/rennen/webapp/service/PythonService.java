package dev.rennen.webapp.service;

import dev.rennen.webapp.dto.MatchingResultResponseVo;

import java.util.List;

public interface PythonService {


    List<String> batchCalcColor(List<String> imagePaths, String pathPrefix);

    List<MatchingResultResponseVo> matchImagesByColor (String color, String colors);

    List<String> batchCalcTexture(List<String> imagePaths, String imageFilePrefix);

    List<MatchingResultResponseVo> matchImagesByTexture (String color, String colors);
}
