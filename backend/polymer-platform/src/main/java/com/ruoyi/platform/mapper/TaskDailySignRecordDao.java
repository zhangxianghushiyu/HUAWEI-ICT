package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskDailySignRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskDailySignRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDailySignRecord record);

    int insertSelective(TaskDailySignRecord record);

    TaskDailySignRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDailySignRecord record);

    int updateByPrimaryKey(TaskDailySignRecord record);


    /**
     * 查询用户最后一次签到信息
     *
     * @param userId
     * @return
     */
    TaskDailySignRecord selectLimitTaskDailySignRecordInfo(@Param("userId") Integer userId);

    /**
     * 根据用户ID、批号查询签到次数
     *
     * @param userId
     * @return
     */
    Integer selectTaskDailySignRecordInfoByBatchNo(@Param("userId") Integer userId, @Param("batchNo") String batchNo);


}