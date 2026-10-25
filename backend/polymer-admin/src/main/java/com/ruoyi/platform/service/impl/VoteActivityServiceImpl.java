package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.VoteActivityMapper;
import com.ruoyi.platform.domain.VoteActivity;
import com.ruoyi.platform.service.IVoteActivityService;

/**
 * 投票活动Service业务层处理
 * 
 * @author lhr
 * @date 2026-05-15
 */
@Service
public class VoteActivityServiceImpl implements IVoteActivityService 
{
    @Autowired
    private VoteActivityMapper voteActivityMapper;

    /**
     * 查询投票活动
     * 
     * @param id 投票活动主键
     * @return 投票活动
     */
    @Override
    public VoteActivity selectVoteActivityById(Long id)
    {
        return voteActivityMapper.selectVoteActivityById(id);
    }

    /**
     * 查询投票活动列表
     * 
     * @param voteActivity 投票活动
     * @return 投票活动
     */
    @Override
    public List<VoteActivity> selectVoteActivityList(VoteActivity voteActivity)
    {
        return voteActivityMapper.selectVoteActivityList(voteActivity);
    }

    /**
     * 新增投票活动
     * 
     * @param voteActivity 投票活动
     * @return 结果
     */
    @Override
    public int insertVoteActivity(VoteActivity voteActivity)
    {
        voteActivity.setCreateTime(DateUtils.getNowDate());
        return voteActivityMapper.insertVoteActivity(voteActivity);
    }

    /**
     * 修改投票活动
     * 
     * @param voteActivity 投票活动
     * @return 结果
     */
    @Override
    public int updateVoteActivity(VoteActivity voteActivity)
    {
        voteActivity.setUpdateTime(DateUtils.getNowDate());
        return voteActivityMapper.updateVoteActivity(voteActivity);
    }

    /**
     * 批量删除投票活动
     * 
     * @param ids 需要删除的投票活动主键
     * @return 结果
     */
    @Override
    public int deleteVoteActivityByIds(Long[] ids)
    {
        return voteActivityMapper.deleteVoteActivityByIds(ids);
    }

    /**
     * 删除投票活动信息
     * 
     * @param id 投票活动主键
     * @return 结果
     */
    @Override
    public int deleteVoteActivityById(Long id)
    {
        return voteActivityMapper.deleteVoteActivityById(id);
    }
}
