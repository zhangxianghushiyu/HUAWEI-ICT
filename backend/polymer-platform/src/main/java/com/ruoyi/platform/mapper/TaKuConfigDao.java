package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaKuConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaKuConfigDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaKuConfig record);

    int insertSelective(TaKuConfig record);

    TaKuConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaKuConfig record);

    int updateByPrimaryKey(TaKuConfig record);

    /**
     * 获取信息
     */
    List<TaKuConfig> selectTaKuAdConfigListAll();
}