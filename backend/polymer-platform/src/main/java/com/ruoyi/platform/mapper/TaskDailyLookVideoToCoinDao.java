package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskDailyLookVideoToCoin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskDailyLookVideoToCoinDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailyLookVideoToCoin record);

    int insertSelective(TaskDailyLookVideoToCoin record);

    TaskDailyLookVideoToCoin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailyLookVideoToCoin record);

    int updateByPrimaryKey(TaskDailyLookVideoToCoin record);

    /**
     * 获取配置信息
     */
    TaskDailyLookVideoToCoin selectTaskDailyLookVideoToCoinInfo();
}