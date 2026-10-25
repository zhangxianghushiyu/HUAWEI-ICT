package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.ParamsConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ParamsConfigDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ParamsConfig record);

    int insertSelective(ParamsConfig record);

    ParamsConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ParamsConfig record);

    int updateByPrimaryKey(ParamsConfig record);

    /**
     * 根据类型获取信息
     */
    List<ParamsConfig> selectParamsConfigListByType(@Param("type") Integer type);
}