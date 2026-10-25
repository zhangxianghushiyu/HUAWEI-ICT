package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.VoteOption;

/**
 * 投票选项Service接口
 * 
 * @author lhr
 * @date 2026-05-15
 */
public interface IVoteOptionService 
{
    /**
     * 查询投票选项
     * 
     * @param id 投票选项主键
     * @return 投票选项
     */
    public VoteOption selectVoteOptionById(Long id);

    /**
     * 查询投票选项列表
     * 
     * @param voteOption 投票选项
     * @return 投票选项集合
     */
    public List<VoteOption> selectVoteOptionList(VoteOption voteOption);

    /**
     * 新增投票选项
     * 
     * @param voteOption 投票选项
     * @return 结果
     */
    public int insertVoteOption(VoteOption voteOption);

    /**
     * 修改投票选项
     * 
     * @param voteOption 投票选项
     * @return 结果
     */
    public int updateVoteOption(VoteOption voteOption);

    /**
     * 批量删除投票选项
     * 
     * @param ids 需要删除的投票选项主键集合
     * @return 结果
     */
    public int deleteVoteOptionByIds(Long[] ids);

    /**
     * 删除投票选项信息
     * 
     * @param id 投票选项主键
     * @return 结果
     */
    public int deleteVoteOptionById(Long id);
}
