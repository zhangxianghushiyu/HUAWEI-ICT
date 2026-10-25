package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskDailyLookDramaToCoin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskDailyLookDramaToCoinDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailyLookDramaToCoin record);

    int insertSelective(TaskDailyLookDramaToCoin record);

    TaskDailyLookDramaToCoin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailyLookDramaToCoin record);

    int updateByPrimaryKey(TaskDailyLookDramaToCoin record);

    /**
     * 查询设置的最大分钟数
     */
    TaskDailyLookDramaToCoin selectMaxTaskDailyLookDramaToCoin();

    /**
     * 获取所有配置信息
     * @return
     */
    List<TaskDailyLookDramaToCoin> selectAllTaskDailyLookDramaToCoin();
}