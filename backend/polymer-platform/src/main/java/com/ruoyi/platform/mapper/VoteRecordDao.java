package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.VoteRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VoteRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VoteRecord record);

    int insertSelective(VoteRecord record);

    VoteRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VoteRecord record);

    int updateByPrimaryKey(VoteRecord record);

    /**
     * 查询用户是否已投票
     */
    VoteRecord selectByUserIdAndActivityId(@Param("userId") Integer userId, 
                                           @Param("activityId") Integer activityId);

    /**
     * 查询用户在某活动中的投票记录列表（多选）
     */
    List<VoteRecord> selectListByUserIdAndActivityId(@Param("userId") Integer userId, 
                                                      @Param("activityId") Integer activityId);

    /**
     * 根据活动ID和选项ID查询记录
     */
    VoteRecord selectByUserIdAndOptionId(@Param("userId") Integer userId, 
                                         @Param("optionId") Integer optionId);

    /**
     * 删除用户的投票记录
     */
    int deleteByUserIdAndActivityId(@Param("userId") Integer userId, 
                                    @Param("activityId") Integer activityId);
}
