package dev.rennen.webapp.mapper;

import dev.rennen.webapp.model.ImageAllDataModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 澶忓槈璇�
* @description 针对表【image_all_data(用于存储所有的训练图片（train 文件夹下）)】的数据库操作Mapper
* @createDate 2024-07-01 17:18:35
* @Entity dev.rennen.webapp.dto.model.ImageAllDataModel
*/

@Mapper
public interface ImageAllDataMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ImageAllDataModel record);

    int insertSelective(ImageAllDataModel record);

    ImageAllDataModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImageAllDataModel record);

    int updateByPrimaryKey(ImageAllDataModel record);

    int insertBatch(List<ImageAllDataModel> imageAllDataModels);

    int countAllImages();

    /**
     * 分批查询
     */
    List<ImageAllDataModel> selectBatch(int offset, int limit);
}
