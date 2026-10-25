package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TaskNewPeopleLookDramaRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskNewPeopleLookDramaRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskNewPeopleLookDramaRecord record);

    int insertSelective(TaskNewPeopleLookDramaRecord record);

    TaskNewPeopleLookDramaRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskNewPeopleLookDramaRecord record);

    int updateByPrimaryKey(TaskNewPeopleLookDramaRecord record);


    /**
     * 查询用户的观看记录信息
     */
    Integer selectLimitTaskNewPeopleLookDramaRecordInfo(@Param("userId") Integer userId);
}