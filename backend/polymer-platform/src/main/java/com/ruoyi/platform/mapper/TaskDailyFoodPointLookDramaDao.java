package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskDailyFoodPointLookDrama;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskDailyFoodPointLookDramaDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailyFoodPointLookDrama record);

    int insertSelective(TaskDailyFoodPointLookDrama record);

    TaskDailyFoodPointLookDrama selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailyFoodPointLookDrama record);

    int updateByPrimaryKey(TaskDailyFoodPointLookDrama record);

    /**
     * 获取饭点看剧配置信息
     */
    List<TaskDailyFoodPointLookDrama> selectAllTaskDailyFoodPointLookDrama();
}