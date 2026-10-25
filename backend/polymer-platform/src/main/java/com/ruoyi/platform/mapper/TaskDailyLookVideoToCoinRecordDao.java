package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskDailyLookVideoToCoinRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskDailyLookVideoToCoinRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailyLookVideoToCoinRecord record);

    int insertSelective(TaskDailyLookVideoToCoinRecord record);

    TaskDailyLookVideoToCoinRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailyLookVideoToCoinRecord record);

    int updateByPrimaryKey(TaskDailyLookVideoToCoinRecord record);


    /**
     * 查看用户的当天观看次数
     */
    TaskDailyLookVideoToCoinRecord selectTaskDailyLookVideoToCoinRecordTheSameDay(@Param("userId") Integer userId);

    /**
     * 加次数
     *
     * @param id
     * @return
     */
    Integer addTaskDailyLookVideoToCoinRecordCount(@Param("id") Integer id);

    Integer selectTotalCount(@Param("userId") Integer userId);
}