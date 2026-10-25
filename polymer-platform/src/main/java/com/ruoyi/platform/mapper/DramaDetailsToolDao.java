package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.DramaDetailsTool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DramaDetailsToolDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DramaDetailsTool record);

    int insertSelective(DramaDetailsTool record);

    DramaDetailsTool selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DramaDetailsTool record);

    int updateByPrimaryKey(DramaDetailsTool record);

    List<DramaDetailsTool> selectDramaDetailsToolByDramaId(@Param("dramaId") Integer dramaId);
}