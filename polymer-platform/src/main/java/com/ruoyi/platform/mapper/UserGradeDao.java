package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.UserGrade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserGradeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserGrade record);

    int insertSelective(UserGrade record);

    UserGrade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserGrade record);

    int updateByPrimaryKey(UserGrade record);

    /**
     * 查询用户最大的等级数信息
     */
    UserGrade selectUserGradeMax(@Param("userId") Long userId);
}