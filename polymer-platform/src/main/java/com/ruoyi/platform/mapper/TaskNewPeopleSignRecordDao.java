package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskNewPeopleSignRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskNewPeopleSignRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskNewPeopleSignRecord record);

    int insertSelective(TaskNewPeopleSignRecord record);

    TaskNewPeopleSignRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskNewPeopleSignRecord record);

    int updateByPrimaryKey(TaskNewPeopleSignRecord record);


    /**
     * 查询用户的签到信息
     */
    List<TaskNewPeopleSignRecord> selectTaskNewPeopleSignRecordInfo(@Param("userId") Integer userId);



    /**
     * 查询用户的签到信息
     */
    Integer selectLimitTaskNewPeopleSignRecordInfo(@Param("userId") Integer userId);
}