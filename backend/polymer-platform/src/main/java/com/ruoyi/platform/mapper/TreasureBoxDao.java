package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TreasureBox;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TreasureBoxDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TreasureBox record);

    int insertSelective(TreasureBox record);

    TreasureBox selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TreasureBox record);

    int updateByPrimaryKey(TreasureBox record);

    /**
     * 查看配置信息
     */
    TreasureBox selectTreasureBox();
}