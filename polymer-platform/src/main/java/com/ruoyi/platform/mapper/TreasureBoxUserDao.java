package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TreasureBoxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TreasureBoxUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TreasureBoxUser record);

    int insertSelective(TreasureBoxUser record);

    TreasureBoxUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TreasureBoxUser record);

    int updateByPrimaryKey(TreasureBoxUser record);

    /**
     * 查询用户今天是不是领取了，查询最近的一次领取信息
     */
    TreasureBoxUser selectLastTreasureBoxUserByUserId(@Param("userId") Integer userId);
}