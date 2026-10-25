package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskDailyAppointDramaCoinRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskDailyAppointDramaCoinRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailyAppointDramaCoinRecord record);

    int insertSelective(TaskDailyAppointDramaCoinRecord record);

    TaskDailyAppointDramaCoinRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailyAppointDramaCoinRecord record);

    int updateByPrimaryKey(TaskDailyAppointDramaCoinRecord record);


    /**
     * 查看用户的当天观看秒数
     */
    TaskDailyAppointDramaCoinRecord selectTaskDailyAppointDramaCoinRecordTheSameDay(@Param("userId") Integer userId);


    /**
     * 加观看秒数
     */
    Integer addAppointDramaSecondInfo(@Param("id") Integer id, @Param("second") Integer second);
}