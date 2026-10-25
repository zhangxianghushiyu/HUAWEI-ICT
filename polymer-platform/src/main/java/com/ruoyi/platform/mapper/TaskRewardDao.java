package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskReward;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskRewardDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskReward record);

    int insertSelective(TaskReward record);

    TaskReward selectByPrimaryKey(Integer id);

    /**
     * 根据类型查询任务信息
     * @param followType
     * @return
     */
    TaskReward selectByPrimaryFollowType(@Param("followType") Integer followType);

    int updateByPrimaryKeySelective(TaskReward record);

    int updateByPrimaryKey(TaskReward record);

}