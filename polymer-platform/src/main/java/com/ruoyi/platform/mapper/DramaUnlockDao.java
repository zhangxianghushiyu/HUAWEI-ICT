package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.DramaUnlock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DramaUnlockDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DramaUnlock record);

    int insertSelective(DramaUnlock record);

    DramaUnlock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DramaUnlock record);

    int updateByPrimaryKey(DramaUnlock record);

    /**
     * 查看配置
     */
    DramaUnlock selectDramaUnlock();
}