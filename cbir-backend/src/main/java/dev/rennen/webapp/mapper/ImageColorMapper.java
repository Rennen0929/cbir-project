package dev.rennen.webapp.mapper;

import dev.rennen.webapp.model.ImageAllDataModel;
import dev.rennen.webapp.model.ImageColorModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 澶忓槈璇�
* @description 针对表【image_color】的数据库操作Mapper
* @createDate 2024-07-01 23:33:26
* @Entity dev.rennen.webapp.model.ImageColorModel
*/
@Mapper
public interface ImageColorMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ImageColorModel record);

    int insertSelective(ImageColorModel record);

    ImageColorModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImageColorModel record);

    int updateByPrimaryKey(ImageColorModel record);

    int insertBatch(List<ImageColorModel> imageColorModels);

    int countAllImages();

    List<ImageColorModel> batchSelect(int offset, int limit);
}
