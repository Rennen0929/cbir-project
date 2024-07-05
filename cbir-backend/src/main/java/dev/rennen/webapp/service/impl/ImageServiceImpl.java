package dev.rennen.webapp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import dev.rennen.webapp.common.constants.CommonConstant;
import dev.rennen.webapp.dto.MatchingResultResponseVo;
import dev.rennen.webapp.mapper.ImageAllDataMapper;
import dev.rennen.webapp.mapper.ImageColorMapper;
import dev.rennen.webapp.mapper.ImageShapeMapper;
import dev.rennen.webapp.mapper.ImageTextureMapper;
import dev.rennen.webapp.model.ImageAllDataModel;
import dev.rennen.webapp.model.ImageColorModel;
import dev.rennen.webapp.model.ImageShapeModel;
import dev.rennen.webapp.model.ImageTextureModel;
import dev.rennen.webapp.service.ImageService;
import dev.rennen.webapp.service.PythonService;
import dev.rennen.webapp.utils.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 夏嘉诚
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Resource
    ImageAllDataMapper imageAllDataMapper;

    @Resource
    ImageColorMapper imageColorMapper;

    @Resource
    PythonService pythonService;

    @Resource
    ImageTextureMapper imageTextureMapper;

    @Resource
    ImageShapeMapper imageShapeMapper;

    @Resource
    ExecutorService taskExecutor;


    @Override
    public List<ImageAllDataModel> batchSelect(int offset, int batchSize) {
        return imageAllDataMapper.selectBatch(offset, batchSize);
    }

    @Override
    public int batchInsertImageColor(List<ImageColorModel> imageColorModels) {
        return imageColorMapper.insertBatch(imageColorModels);
    }

    @Override
    public List<MatchingResultResponseVo> matchImagesByColor(String fileName, String pathPrefix) {
        // 1. 获取上传图片的颜色信息
        List<String> colorInfo = pythonService.batchCalcColor(Lists.asList(fileName, new String[0]), pathPrefix);
        if (CollectionUtil.isEmpty(colorInfo)) {
            log.error("获取颜色信息失败");
            return Lists.newArrayList();
        }
        String pendingMatchColorInfo = colorInfo.get(0);
        int count = imageColorMapper.countAllImages();
        List<MatchingResultResponseVo> result = Lists.newArrayList();
        for (int i = 0; i < count; i += CommonConstant.BATCH_PROCESS_SIZE) {
            log.info("匹配数据库中的所有图片颜色信息, 当前进度: {}/{}", i, count);
            List<ImageColorModel> colorModels = imageColorMapper.batchSelect(i, CommonConstant.BATCH_PROCESS_SIZE);
            String colorInfoInDatabase = JsonUtil.toJSONString(colorModels);
            log.info("从数据库中获取到图片信息，正在执行 Python 脚本匹配图片颜色信息");
            List<MatchingResultResponseVo> batchResult = pythonService.matchImagesByFeature(pendingMatchColorInfo, colorInfoInDatabase, "color");
            result.addAll(batchResult);
        }
        log.info("匹配数据库中的所有图片颜色信息完成, 共计算了 {} 张图片", count);
        return result;
    }

    @Override
    public List<MatchingResultResponseVo> matchImagesByTexture(String fileName, String pathPrefix) {
        // 1. 获取上传图片的颜色信息
        List<String> textureInfo = pythonService.batchCalcTexture(Lists.asList(fileName, new String[0]), pathPrefix);
        if (CollectionUtil.isEmpty(textureInfo)) {
            log.error("获取纹理信息失败");
            return Lists.newArrayList();
        }
        String pendingMatchTextureInfo = textureInfo.get(0);
        int count = imageTextureMapper.countAllImages();
        List<MatchingResultResponseVo> result = Lists.newArrayList();
        for (int i = 0; i < count; i += CommonConstant.BATCH_PROCESS_SIZE) {
            log.info("匹配数据库中的所有图片纹理信息, 当前进度: {}/{}", i, count);
            List<ImageTextureModel> textureModels = imageTextureMapper.batchSelect(i, CommonConstant.BATCH_PROCESS_SIZE);
            String colorInfoInDatabase = JsonUtil.toJSONString(textureModels);
            log.info("从数据库中获取到图片信息，正在执行 Python 脚本匹配图片纹理信息");
            List<MatchingResultResponseVo> batchResult = pythonService.matchImagesByFeature(pendingMatchTextureInfo, colorInfoInDatabase, "texture");
            result.addAll(batchResult);
        }
        log.info("匹配数据库中的所有图片纹理信息完成, 共计算了 {} 张图片", count);
        return result;
    }

    @Override
    public int batchInsertImageTexture(List<ImageTextureModel> imageTextureModels) {
        return imageTextureMapper.batchInsert(imageTextureModels);
    }

    @Override
    public int batchInsertImageShape(List<ImageShapeModel> imageShapeModels) {
        return imageShapeMapper.batchInsert(imageShapeModels);
    }

    @Override
    public List<MatchingResultResponseVo> matchImagesByShape(String fileName, String pathPrefix) {
        // 1. 获取上传图片的形状信息
        List<String> shapeInfo = pythonService.batchCalcShape(Lists.asList(fileName, new String[0]), pathPrefix);
        if (CollectionUtil.isEmpty(shapeInfo)) {
            log.error("获取形状信息失败");
            return Lists.newArrayList();
        }
        String pendingMatchShapeInfo = shapeInfo.get(0);
        int count = imageShapeMapper.countAllImages();
        List<MatchingResultResponseVo> result = Lists.newArrayList();
        for (int i = 0; i < count; i += CommonConstant.BATCH_PROCESS_SIZE) {
            log.info("匹配数据库中的所有图片形状信息, 当前进度: {}/{}", i, count);
            List<ImageShapeModel> shapeModels = imageShapeMapper.batchSelect(i, CommonConstant.BATCH_PROCESS_SIZE);
            String shapeInfoInDatabase = JsonUtil.toJSONString(shapeModels);
            log.info("从数据库中获取到图片信息，正在执行 Python 脚本匹配图片形状信息");
            List<MatchingResultResponseVo> batchResult = pythonService.matchImagesByFeature(pendingMatchShapeInfo, shapeInfoInDatabase, "shape");
            result.addAll(batchResult);
        }
        log.info("匹配数据库中的所有图片形状信息完成, 共计算了 {} 张图片", count);
        return result;
    }

    @Override
    public List<MatchingResultResponseVo> matchImagesByMix(String fileName, String pathPrefix) {


        // 创建Callable任务
        Callable<List<MatchingResultResponseVo>> shapeTask = () -> matchImagesByShape(fileName, pathPrefix);
        Callable<List<MatchingResultResponseVo>> colorTask = () -> matchImagesByColor(fileName, pathPrefix);
        Callable<List<MatchingResultResponseVo>> textureTask = () -> matchImagesByTexture(fileName, pathPrefix);

        // 获取结果
        List<MatchingResultResponseVo> shapeList = Lists.newArrayList();
        List<MatchingResultResponseVo> colorList = Lists.newArrayList();
        List<MatchingResultResponseVo> textureList = Lists.newArrayList();

        try {
            // 提交任务并获取Future对象
            Future<List<MatchingResultResponseVo>> shapeFuture = taskExecutor.submit(shapeTask);
            Future<List<MatchingResultResponseVo>> colorFuture = taskExecutor.submit(colorTask);
            Future<List<MatchingResultResponseVo>> textureFuture = taskExecutor.submit(textureTask);

            // 获取结果
            shapeList = shapeFuture.get();
            colorList = colorFuture.get();
            textureList = textureFuture.get();

        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occurred while executing tasks: {}", e.getMessage());
        }

        Map<Long, MatchingResultResponseVo> shapeMap = shapeList.stream().collect(Collectors.toMap(MatchingResultResponseVo::getImageId, Function.identity()));
        Map<Long, MatchingResultResponseVo> colorMap = colorList.stream().collect(Collectors.toMap(MatchingResultResponseVo::getImageId, Function.identity()));
        Map<Long, MatchingResultResponseVo> textureMap = textureList.stream().collect(Collectors.toMap(MatchingResultResponseVo::getImageId, Function.identity()));

        List<MatchingResultResponseVo> result = Lists.newArrayList();
        for (Long key : shapeMap.keySet()) {
            if (colorMap.containsKey(key) && textureMap.containsKey(key)) {
                MatchingResultResponseVo vo = new MatchingResultResponseVo();
                vo.setImageId(key);
                vo.setPath(shapeMap.get(key).getPath());
                vo.setSimilarity(shapeMap.get(key).getSimilarity() * 0.2 + colorMap.get(key).getSimilarity() * 0.2 + textureMap.get(key).getSimilarity() * 0.6);
                result.add(vo);
            }
        }
        result.sort(Comparator.reverseOrder());
        return fillPath(result.subList(0, Math.min(result.size(), CommonConstant.RETURN_SIZE)));

    }

    @Override
    public List<MatchingResultResponseVo> fillPath (List<MatchingResultResponseVo> list) {
        for (MatchingResultResponseVo vo : list) {
            Optional.of(imageAllDataMapper.selectByPrimaryKey(vo.getImageId()))
                    .ifPresent(imageAllDataModel -> vo.setPath(CommonConstant.IMAGE_HOST_URL_PREFIX + imageAllDataModel.getPath()));
        }
        return list;
    }

}
