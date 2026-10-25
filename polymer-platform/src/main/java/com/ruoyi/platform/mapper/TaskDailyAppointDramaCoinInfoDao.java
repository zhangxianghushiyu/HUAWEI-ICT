package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskDailyAppointDramaCoinInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskDailyAppointDramaCoinInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailyAppointDramaCoinInfo record);

    int insertSelective(TaskDailyAppointDramaCoinInfo record);

    TaskDailyAppointDramaCoinInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailyAppointDramaCoinInfo record);

    int updateByPrimaryKey(TaskDailyAppointDramaCoinInfo record);

    /**
     * 查询配置信息
     */
    TaskDailyAppointDramaCoinInfo selectTaskDailyAppointDramaCoinInfoInfo();
}