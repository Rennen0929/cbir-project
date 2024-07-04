package dev.rennen.webapp.service;

import dev.rennen.webapp.dto.MatchingResultResponseVo;
import dev.rennen.webapp.model.ImageAllDataModel;
import dev.rennen.webapp.model.ImageColorModel;
import dev.rennen.webapp.model.ImageTextureModel;

import java.util.List;

/**
 * @author 夏嘉诚
 */
public interface ImageService {

    List<ImageAllDataModel> batchSelect(int offset, int batchSize);

    int batchInsertImageColor(List<ImageColorModel> imageColorModels);

    /**
     * 根据颜色，将上传的图片和数据库中的图片进行比对
     *
     * @param fileName       上传图片的路径
     * @param pathPrefix
     * @return
     */
    List<MatchingResultResponseVo> matchImagesByColor(String fileName, String pathPrefix);

    /**
     * 根据纹理匹配图片
     * @param fileName 上传图片的路径
     * @param pathPrefix 图片路径前缀
     * @return 匹配结果
     */
    List<MatchingResultResponseVo> matchImagesByTexture(String fileName, String pathPrefix);

    int batchInsertImageTexture(List<ImageTextureModel> imageTextureModels);
}
