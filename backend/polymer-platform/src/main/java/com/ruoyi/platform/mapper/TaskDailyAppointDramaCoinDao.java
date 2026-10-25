package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskDailyAppointDramaCoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskDailyAppointDramaCoinDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailyAppointDramaCoin record);

    int insertSelective(TaskDailyAppointDramaCoin record);

    TaskDailyAppointDramaCoin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailyAppointDramaCoin record);

    int updateByPrimaryKey(TaskDailyAppointDramaCoin record);

    /**
     * 查看是不是指定的剧
     */
    TaskDailyAppointDramaCoin selectTaskDailyAppointDramaCoinByDramaId(@Param("dramaId")Integer dramaId);
}