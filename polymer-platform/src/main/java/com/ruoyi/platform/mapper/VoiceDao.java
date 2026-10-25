package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.Voice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VoiceDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Voice record);

    int insertSelective(Voice record);

    Voice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Voice record);

    int updateByPrimaryKey(Voice record);

    /**
     * 根据类型获取信息
     */
    Voice selectVoiceByType(@Param("type") Integer type);
}