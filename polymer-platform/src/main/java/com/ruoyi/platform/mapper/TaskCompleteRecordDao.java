package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskCompleteRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskCompleteRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskCompleteRecord record);

    int insertSelective(TaskCompleteRecord record);

    TaskCompleteRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskCompleteRecord record);

    int updateByPrimaryKey(TaskCompleteRecord record);

    /**
     * 查看用户有没有完成此新人任务
     */
    TaskCompleteRecord selectTaskCompleteRecordByTypeNewPeople(@Param("userId") Integer userId,
                                                               @Param("taskFollowType") Integer taskFollowType);


    /**
     * 查看用户有没有完成此新人任务（按照条数查询）
     */
    List<TaskCompleteRecord> selectTaskCompleteRecordByTypeNewPeopleSIgn(@Param("userId") Integer userId,
                                                     @Param("taskFollowType") Integer taskFollowType);


    /**
     * 查看用户有没有完成此每日任务
     */
    TaskCompleteRecord selectTaskCompleteRecordByTypeDaily(@Param("userId") Integer userId,
                                                               @Param("taskFollowType") Integer taskFollowType);
}