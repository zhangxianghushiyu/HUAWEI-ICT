package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.GradeConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GradeConfigDao {
    int deleteByPrimaryKey(Integer id);

    int insert(GradeConfig record);

    int insertSelective(GradeConfig record);

    GradeConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GradeConfig record);

    int updateByPrimaryKey(GradeConfig record);

    /**
     * 查询某一等级的配置信息
     */
    GradeConfig selectGradeConfig(@Param("grade") Integer grade);
}