package dev.rennen.webapp.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import dev.rennen.webapp.common.constants.CommonConstant;
import dev.rennen.webapp.mapper.ImageAllDataMapper;
import dev.rennen.webapp.model.ImageAllDataModel;
import dev.rennen.webapp.model.ImageColorModel;
import dev.rennen.webapp.model.ImageShapeModel;
import dev.rennen.webapp.model.ImageTextureModel;
import dev.rennen.webapp.service.ImageService;
import dev.rennen.webapp.service.PythonService;
import dev.rennen.webapp.dto.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

/**
 * @author 夏嘉诚
 * 该类提供一些数据准备接口
 */
@RestController
@RequestMapping("/prepare")
@Slf4j
public class DataPreparationController {

    @Resource
    ImageAllDataMapper imageAllDataMapper;

    @Resource
    ImageService imageService;

    @Resource
    PythonService pythonService;

    @GetMapping("/addAllImages")
    public Result<String> add() {
        for (String category : CommonConstant.CATEGORIES) {
            List<ImageAllDataModel> imageAllDataModels = Lists.newArrayList();
            for (int i = 1; i <= CommonConstant.NUM_IMAGES_PER_CATEGORY; i++) {
                ImageAllDataModel imageAllDataModel = new ImageAllDataModel();
                imageAllDataModel.setCategory(category);
                // 路径为 /category/000i.png 的格式，前面需要有前导零填充
                imageAllDataModel.setPath("/" + category + "/" + String.format("%04d", i) + ".png");
                imageAllDataModel.setSeqNum(i);
                imageAllDataModels.add(imageAllDataModel);
            }
            imageAllDataMapper.insertBatch(imageAllDataModels);
        }
        return Result.success("success");
    }

    /**
     * 计算数据库中的所有图片颜色信息
     * @return 计算结果
     */
    @GetMapping("/calculateColor")
    public Result<String> calculateColor() {
        int count = imageAllDataMapper.countAllImages();
        for (int i = 0; i < count; i += CommonConstant.BATCH_PROCESS_SIZE) {
            List<ImageAllDataModel> models = imageService.batchSelect(i, CommonConstant.BATCH_PROCESS_SIZE);
            List<String> imagePaths = models.stream().map(ImageAllDataModel::getPath).toList();
            List<String> res = pythonService.batchCalcColor(imagePaths, CommonConstant.IMAGE_FILE_PREFIX);
            List<ImageColorModel> imageColorModels = Lists.newArrayList();
            for (int j = 0; j < res.size(); j++) {
                ImageColorModel imageColorModel = new ImageColorModel();
                imageColorModel.setImageId(models.get(j).getId());
                imageColorModel.setColor(res.get(j));
                imageColorModels.add(imageColorModel);
            }
            if (CollectionUtil.isEmpty(imageColorModels)) {
                log.error("获取颜色信息失败，imageColorModels 为空");
                return Result.error(500, "获取颜色信息失败，imageColorModels 为空");
            }
            imageService.batchInsertImageColor(imageColorModels);
        }
        log.info("计算数据库中的所有图片颜色信息完成, 共计算了 {} 张图片", count);
        return Result.success("success");
    }

    @GetMapping("/calculateTexture")
    public Result<String> calculateTexture() {
        int count = imageAllDataMapper.countAllImages();
        for (int i = 0; i < count; i += CommonConstant.BATCH_PROCESS_SIZE) {
            List<ImageAllDataModel> models = imageService.batchSelect(i, CommonConstant.BATCH_PROCESS_SIZE);
            List<String> imagePaths = models.stream().map(ImageAllDataModel::getPath).toList();
            List<String> res = pythonService.batchCalcTexture(imagePaths, CommonConstant.IMAGE_FILE_PREFIX);
            List<ImageTextureModel> imageTextureModels = Lists.newArrayList();
            for (int j = 0; j < res.size(); j++) {
                ImageTextureModel imageTextureModel = new ImageTextureModel();
                imageTextureModel.setImageId(models.get(j).getId());
                imageTextureModel.setTexture(res.get(j));
                imageTextureModels.add(imageTextureModel);
            }
            if (CollectionUtil.isEmpty(imageTextureModels)) {
                log.error("获取纹理信息失败，imageTextureModels 为空");
                return Result.error(500, "获取纹理信息失败，imageTextureModels 为空");
            }
            imageService.batchInsertImageTexture(imageTextureModels);
        }
        log.info("计算数据库中的所有图片纹理信息完成, 共计算了 {} 张图片", count);
        return Result.success("success");
    }

    @GetMapping("/calculateShape")
    public Result<String> calculateShape() {
        int count = imageAllDataMapper.countAllImages();
        for (int i = 0; i < count; i += CommonConstant.BATCH_PROCESS_SIZE) {
            List<ImageAllDataModel> models = imageService.batchSelect(i, CommonConstant.BATCH_PROCESS_SIZE);
            List<String> imagePaths = models.stream().map(ImageAllDataModel::getPath).toList();
            List<String> res = pythonService.batchCalcShape(imagePaths, CommonConstant.IMAGE_FILE_PREFIX);
            List<ImageShapeModel> imageShapeModels = Lists.newArrayList();
            for (int j = 0; j < res.size(); j++) {
                ImageShapeModel imageShapeModel = new ImageShapeModel();
                imageShapeModel.setImageId(models.get(j).getId());
                imageShapeModel.setShape(res.get(j));
                imageShapeModels.add(imageShapeModel);
            }
            if (CollectionUtil.isEmpty(imageShapeModels)) {
                log.error("获取形状信息失败，imageShapeModels 为空");
                return Result.error(500, "获取形状信息失败，imageShapeModels 为空");
            }
            imageService.batchInsertImageShape(imageShapeModels);
        }
        log.info("计算数据库中的所有图片形状信息完成, 共计算了 {} 张图片", count);
        return Result.success("success");
    }
}
