package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.ConvertRatio;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConvertRatioDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ConvertRatio record);

    int insertSelective(ConvertRatio record);

    ConvertRatio selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConvertRatio record);

    int updateByPrimaryKey(ConvertRatio record);

    /**
     * 获取比例
     */
    ConvertRatio selectConvertRatioInfo();
}