package dev.rennen.webapp.mapper;


import dev.rennen.webapp.model.ImageTextureModel;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 澶忓槈璇�
* @description 针对表【image_texture】的数据库操作Mapper
* @createDate 2024-07-04 14:56:25
* @Entity generator.domain.ImageTextureModel
*/
@Mapper
public interface ImageTextureMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ImageTextureModel record);

    int insertSelective(ImageTextureModel record);

    ImageTextureModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImageTextureModel record);

    int updateByPrimaryKey(ImageTextureModel record);

}
