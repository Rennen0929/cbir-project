package dev.rennen.webapp.service;

import dev.rennen.webapp.dto.MatchingResultResponseVo;

import java.util.List;

public interface PythonService {


    List<String> batchCalcColor(List<String> imagePaths, String pathPrefix);

    List<MatchingResultResponseVo> matchImagesByFeature(String vector, String datas, String dimension);

    List<String> batchCalcTexture(List<String> imagePaths, String imageFilePrefix);

}
