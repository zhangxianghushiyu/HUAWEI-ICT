package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskDailyLookDramaToCoinRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskDailyLookDramaToCoinRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailyLookDramaToCoinRecord record);

    int insertSelective(TaskDailyLookDramaToCoinRecord record);

    TaskDailyLookDramaToCoinRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailyLookDramaToCoinRecord record);

    int updateByPrimaryKey(TaskDailyLookDramaToCoinRecord record);

    /**
     * 查看用户的当天观看秒数
     */
    TaskDailyLookDramaToCoinRecord selectTaskDailyLookDramaToCoinRecordTheSameDay(@Param("userId") Integer userId);

    /**
     * 加观看描述
     */
    Integer addLookSecondInfo(@Param("id") Integer id, @Param("second") Integer second);
}