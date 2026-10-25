package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.VoteOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VoteOptionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(VoteOption record);

    int insertSelective(VoteOption record);

    VoteOption selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VoteOption record);

    int updateByPrimaryKey(VoteOption record);

    /**
     * 根据活动ID查询选项列表
     */
    List<VoteOption> selectByActivityId(@Param("activityId") Integer activityId);

    /**
     * 增加选项票数
     */
    int addVoteCount(@Param("id") Integer id);

    /**
     * 减少选项票数
     */
    int subVoteCount(@Param("id") Integer id);

    /**
     * 批量增加选项票数
     */
    int batchAddVoteCount(@Param("optionIds") List<Integer> optionIds);

    /**
     * 批量减少选项票数
     */
    int batchSubVoteCount(@Param("optionIds") List<Integer> optionIds);
}
