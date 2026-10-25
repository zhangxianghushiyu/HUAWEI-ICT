package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.Barrier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BarrierDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Barrier record);

    int insertSelective(Barrier record);

    Barrier selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Barrier record);

    int updateByPrimaryKey(Barrier record);

    /**
     * 查询最小的一关数据
     */
    Barrier selectMinBarrier();

    /**
     * 查询大于某一关的最小数据
     */
    Barrier selectMinBarrierByOldValue(@Param("oldValue")Integer oldValue);

    /**
     * 查询某一关数据
     */
    Barrier selectBarrierInfo(@Param("barrier") Integer barrier);
}