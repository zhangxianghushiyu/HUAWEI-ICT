package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.ApplyDestroy;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplyDestroyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplyDestroy record);

    int insertSelective(ApplyDestroy record);

    ApplyDestroy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplyDestroy record);

    int updateByPrimaryKey(ApplyDestroy record);
}