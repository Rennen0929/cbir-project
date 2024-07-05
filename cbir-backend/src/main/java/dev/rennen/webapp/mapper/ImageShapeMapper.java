package dev.rennen.webapp.mapper;


import dev.rennen.webapp.model.ImageShapeModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 澶忓槈璇�
* @description 针对表【image_shape】的数据库操作Mapper
* @createDate 2024-07-04 21:50:16
* @Entity generator.domain.ImageShapeModel
*/
@Mapper
public interface ImageShapeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ImageShapeModel record);

    int insertSelective(ImageShapeModel record);

    ImageShapeModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImageShapeModel record);

    int updateByPrimaryKey(ImageShapeModel record);

    int batchInsert(List<ImageShapeModel> imageShapeModels);

    int countAllImages();

    List<ImageShapeModel> batchSelect(int offset, int limit);
}
