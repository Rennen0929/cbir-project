package dev.rennen.webapp.mapper;

import dev.rennen.webapp.model.UserModel;
import org.apache.ibatis.annotations.Mapper;

/**
* @author renne
* @description 针对表【user-test】的数据库操作Mapper
* @createDate 2024-07-01 11:32:35
* @Entity dev.rennen.webapp.dto.model.UserModel
*/
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    UserModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);

}
