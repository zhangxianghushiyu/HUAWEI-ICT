package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.VoteRecordMapper;
import com.ruoyi.platform.domain.VoteRecord;
import com.ruoyi.platform.service.IVoteRecordService;

/**
 * 投票记录Service业务层处理
 * 
 * @author lhr
 * @date 2026-05-15
 */
@Service
public class VoteRecordServiceImpl implements IVoteRecordService 
{
    @Autowired
    private VoteRecordMapper voteRecordMapper;

    /**
     * 查询投票记录
     * 
     * @param id 投票记录主键
     * @return 投票记录
     */
    @Override
    public VoteRecord selectVoteRecordById(Long id)
    {
        return voteRecordMapper.selectVoteRecordById(id);
    }

    /**
     * 查询投票记录列表
     * 
     * @param voteRecord 投票记录
     * @return 投票记录
     */
    @Override
    public List<VoteRecord> selectVoteRecordList(VoteRecord voteRecord)
    {
        return voteRecordMapper.selectVoteRecordList(voteRecord);
    }

    /**
     * 新增投票记录
     * 
     * @param voteRecord 投票记录
     * @return 结果
     */
    @Override
    public int insertVoteRecord(VoteRecord voteRecord)
    {
        voteRecord.setCreateTime(DateUtils.getNowDate());
        return voteRecordMapper.insertVoteRecord(voteRecord);
    }

    /**
     * 修改投票记录
     * 
     * @param voteRecord 投票记录
     * @return 结果
     */
    @Override
    public int updateVoteRecord(VoteRecord voteRecord)
    {
        voteRecord.setUpdateTime(DateUtils.getNowDate());
        return voteRecordMapper.updateVoteRecord(voteRecord);
    }

    /**
     * 批量删除投票记录
     * 
     * @param ids 需要删除的投票记录主键
     * @return 结果
     */
    @Override
    public int deleteVoteRecordByIds(Long[] ids)
    {
        return voteRecordMapper.deleteVoteRecordByIds(ids);
    }

    /**
     * 删除投票记录信息
     * 
     * @param id 投票记录主键
     * @return 结果
     */
    @Override
    public int deleteVoteRecordById(Long id)
    {
        return voteRecordMapper.deleteVoteRecordById(id);
    }
}
