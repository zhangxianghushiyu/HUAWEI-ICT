package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.UserDeactivate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDeactivateDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDeactivate record);

    int insertSelective(UserDeactivate record);

    UserDeactivate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDeactivate record);

    int updateByPrimaryKey(UserDeactivate record);
}