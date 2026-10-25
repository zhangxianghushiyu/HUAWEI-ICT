package com.ruoyi.platform.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.AnswerRecord;
import com.ruoyi.platform.mapper.AnswerRecordMapper;
import com.ruoyi.platform.service.IAnswerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 答题记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
@Service
public class AnswerRecordServiceImpl implements IAnswerRecordService 
{
    @Autowired
    private AnswerRecordMapper answerRecordMapper;

    /**
     * 查询答题记录
     * 
     * @param id 答题记录主键
     * @return 答题记录
     */
    @Override
    public AnswerRecord selectAnswerRecordById(Long id)
    {
        return answerRecordMapper.selectAnswerRecordById(id);
    }

    /**
     * 查询答题记录列表
     * 
     * @param answerRecord 答题记录
     * @return 答题记录
     */
    @Override
    public List<AnswerRecord> selectAnswerRecordList(AnswerRecord answerRecord)
    {
        return answerRecordMapper.selectAnswerRecordList(answerRecord);
    }

    /**
     * 新增答题记录
     * 
     * @param answerRecord 答题记录
     * @return 结果
     */
    @Override
    public int insertAnswerRecord(AnswerRecord answerRecord)
    {
        answerRecord.setCreateTime(DateUtils.getNowDate());
        return answerRecordMapper.insertAnswerRecord(answerRecord);
    }

    /**
     * 修改答题记录
     * 
     * @param answerRecord 答题记录
     * @return 结果
     */
    @Override
    public int updateAnswerRecord(AnswerRecord answerRecord)
    {
        answerRecord.setUpdateTime(DateUtils.getNowDate());
        return answerRecordMapper.updateAnswerRecord(answerRecord);
    }

    /**
     * 批量删除答题记录
     * 
     * @param ids 需要删除的答题记录主键
     * @return 结果
     */
    @Override
    public int deleteAnswerRecordByIds(Long[] ids)
    {
        return answerRecordMapper.deleteAnswerRecordByIds(ids);
    }

    /**
     * 删除答题记录信息
     * 
     * @param id 答题记录主键
     * @return 结果
     */
    @Override
    public int deleteAnswerRecordById(Long id)
    {
        return answerRecordMapper.deleteAnswerRecordById(id);
    }
}
