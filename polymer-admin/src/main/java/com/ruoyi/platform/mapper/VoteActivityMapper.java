package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.VoteActivity;

/**
 * 投票活动Mapper接口
 * 
 * @author lhr
 * @date 2026-05-15
 */
public interface VoteActivityMapper 
{
    /**
     * 查询投票活动
     * 
     * @param id 投票活动主键
     * @return 投票活动
     */
    public VoteActivity selectVoteActivityById(Long id);

    /**
     * 查询投票活动列表
     * 
     * @param voteActivity 投票活动
     * @return 投票活动集合
     */
    public List<VoteActivity> selectVoteActivityList(VoteActivity voteActivity);

    /**
     * 新增投票活动
     * 
     * @param voteActivity 投票活动
     * @return 结果
     */
    public int insertVoteActivity(VoteActivity voteActivity);

    /**
     * 修改投票活动
     * 
     * @param voteActivity 投票活动
     * @return 结果
     */
    public int updateVoteActivity(VoteActivity voteActivity);

    /**
     * 删除投票活动
     * 
     * @param id 投票活动主键
     * @return 结果
     */
    public int deleteVoteActivityById(Long id);

    /**
     * 批量删除投票活动
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVoteActivityByIds(Long[] ids);
}
