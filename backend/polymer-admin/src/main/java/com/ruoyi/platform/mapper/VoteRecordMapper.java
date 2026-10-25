package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.VoteRecord;

/**
 * 投票记录Mapper接口
 * 
 * @author lhr
 * @date 2026-05-15
 */
public interface VoteRecordMapper 
{
    /**
     * 查询投票记录
     * 
     * @param id 投票记录主键
     * @return 投票记录
     */
    public VoteRecord selectVoteRecordById(Long id);

    /**
     * 查询投票记录列表
     * 
     * @param voteRecord 投票记录
     * @return 投票记录集合
     */
    public List<VoteRecord> selectVoteRecordList(VoteRecord voteRecord);

    /**
     * 新增投票记录
     * 
     * @param voteRecord 投票记录
     * @return 结果
     */
    public int insertVoteRecord(VoteRecord voteRecord);

    /**
     * 修改投票记录
     * 
     * @param voteRecord 投票记录
     * @return 结果
     */
    public int updateVoteRecord(VoteRecord voteRecord);

    /**
     * 删除投票记录
     * 
     * @param id 投票记录主键
     * @return 结果
     */
    public int deleteVoteRecordById(Long id);

    /**
     * 批量删除投票记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVoteRecordByIds(Long[] ids);
}
