package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.VoteOptionMapper;
import com.ruoyi.platform.domain.VoteOption;
import com.ruoyi.platform.service.IVoteOptionService;

/**
 * 投票选项Service业务层处理
 * 
 * @author lhr
 * @date 2026-05-15
 */
@Service
public class VoteOptionServiceImpl implements IVoteOptionService 
{
    @Autowired
    private VoteOptionMapper voteOptionMapper;

    /**
     * 查询投票选项
     * 
     * @param id 投票选项主键
     * @return 投票选项
     */
    @Override
    public VoteOption selectVoteOptionById(Long id)
    {
        return voteOptionMapper.selectVoteOptionById(id);
    }

    /**
     * 查询投票选项列表
     * 
     * @param voteOption 投票选项
     * @return 投票选项
     */
    @Override
    public List<VoteOption> selectVoteOptionList(VoteOption voteOption)
    {
        return voteOptionMapper.selectVoteOptionList(voteOption);
    }

    /**
     * 新增投票选项
     * 
     * @param voteOption 投票选项
     * @return 结果
     */
    @Override
    public int insertVoteOption(VoteOption voteOption)
    {
        voteOption.setCreateTime(DateUtils.getNowDate());
        return voteOptionMapper.insertVoteOption(voteOption);
    }

    /**
     * 修改投票选项
     * 
     * @param voteOption 投票选项
     * @return 结果
     */
    @Override
    public int updateVoteOption(VoteOption voteOption)
    {
        voteOption.setUpdateTime(DateUtils.getNowDate());
        return voteOptionMapper.updateVoteOption(voteOption);
    }

    /**
     * 批量删除投票选项
     * 
     * @param ids 需要删除的投票选项主键
     * @return 结果
     */
    @Override
    public int deleteVoteOptionByIds(Long[] ids)
    {
        return voteOptionMapper.deleteVoteOptionByIds(ids);
    }

    /**
     * 删除投票选项信息
     * 
     * @param id 投票选项主键
     * @return 结果
     */
    @Override
    public int deleteVoteOptionById(Long id)
    {
        return voteOptionMapper.deleteVoteOptionById(id);
    }
}
