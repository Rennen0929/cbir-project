package dev.rennen.webapp.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSON;
import com.google.common.collect.Lists;
import dev.rennen.webapp.common.constants.CommonConstant;
import dev.rennen.webapp.dto.MatchingResultResponseVo;
import dev.rennen.webapp.mapper.ImageAllDataMapper;
import dev.rennen.webapp.mapper.ImageColorMapper;
import dev.rennen.webapp.model.ImageAllDataModel;
import dev.rennen.webapp.model.ImageColorModel;
import dev.rennen.webapp.service.ImageService;
import dev.rennen.webapp.service.PythonService;
import dev.rennen.webapp.dto.Result;
import dev.rennen.webapp.utils.BoundedPriorityQueue;
import dev.rennen.webapp.utils.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

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
        // 每次和 50 张照片匹配
        int count = imageColorMapper.countAllImages();
//        BoundedPriorityQueue<MatchingResultResponseVo> resultQueue = new BoundedPriorityQueue<>(CommonConstant.RETURN_SIZE, Comparator.comparingDouble(MatchingResultResponseVo::getSimilarity));
//        PriorityQueue<MatchingResultResponseVo> resultQueue = new PriorityQueue<>(Comparator.reverseOrder());
        List<MatchingResultResponseVo> result = Lists.newArrayList();
        for (int i = 0; i < count; i += CommonConstant.BATCH_PROCESS_SIZE) {
            log.info("匹配数据库中的所有图片颜色信息, 当前进度: {}/{}", i, count);
            List<ImageColorModel> colorModels = imageColorMapper.batchSelect(i, CommonConstant.BATCH_PROCESS_SIZE);
            String colorInfoInDatabase = JsonUtil.toJSONString(colorModels);
            log.info("从数据库中获取到图片信息，正在执行 Python 脚本匹配图片颜色信息");
            List<MatchingResultResponseVo> batchResult = pythonService.matchImagesByColor(pendingMatchColorInfo, colorInfoInDatabase);
            result.addAll(batchResult);
        }
        result.sort(Comparator.reverseOrder());
        log.info("匹配数据库中的所有图片颜色信息完成, 共计算了 {} 张图片", count);
        return fillPath(result.subList(0, Math.min(result.size(), CommonConstant.RETURN_SIZE)));
    }

    @Override
    public List<MatchingResultResponseVo> matchImagesByTexture(String fileName, String pathPrefix) {
        // 1. 获取上传文件的纹理信息

    }

    private List<MatchingResultResponseVo> fillPath (List<MatchingResultResponseVo> list) {
        for (MatchingResultResponseVo vo : list) {
            Optional.of(imageAllDataMapper.selectByPrimaryKey(vo.getImageId()))
                    .ifPresent(imageAllDataModel -> vo.setPath(CommonConstant.IMAGE_HOST_URL_PREFIX + imageAllDataModel.getPath()));
        }
        return list;
    }
}
