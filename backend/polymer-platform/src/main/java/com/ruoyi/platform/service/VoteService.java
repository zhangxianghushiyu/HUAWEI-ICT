package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.VoteActivityDetailVO;
import com.ruoyi.platform.vo.VoteActivityListVO;

import java.util.List;

public interface VoteService {

    /**
     * 查询投票活动列表
     */
    List<VoteActivityListVO> getVoteActivityList();

    /**
     * 查询投票活动详情
     */
    VoteActivityDetailVO getVoteActivityDetail(Integer activityId);

    /**
     * 投票（单选）
     */
    void vote(Integer activityId, Integer optionId);

    /**
     * 投票（多选）
     */
    void voteMultiple(Integer activityId, List<Integer> optionIds);

    /**
     * 取消投票（单选）
     */
    void cancelVote(Integer activityId);

    /**
     * 取消投票（多选）
     */
    void cancelVoteMultiple(Integer activityId);
}
