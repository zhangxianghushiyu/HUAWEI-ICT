package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.DramaTool;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DramaToolDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DramaTool record);

    int insertSelective(DramaTool record);

    DramaTool selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DramaTool record);

    int updateByPrimaryKey(DramaTool record);

    List<DramaTool> selectAll(String[] ids);
}