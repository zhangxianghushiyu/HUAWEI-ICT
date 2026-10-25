package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.Detection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DetectionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Detection record);

    int insertSelective(Detection record);

    Detection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Detection record);

    int updateByPrimaryKey(Detection record);

    List<Detection> selectDetectionAll();
}