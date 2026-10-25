package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.AnswerRecord;

import java.util.List;

/**
 * 答题记录Service接口
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
public interface IAnswerRecordService 
{
    /**
     * 查询答题记录
     * 
     * @param id 答题记录主键
     * @return 答题记录
     */
    public AnswerRecord selectAnswerRecordById(Long id);

    /**
     * 查询答题记录列表
     * 
     * @param answerRecord 答题记录
     * @return 答题记录集合
     */
    public List<AnswerRecord> selectAnswerRecordList(AnswerRecord answerRecord);

    /**
     * 新增答题记录
     * 
     * @param answerRecord 答题记录
     * @return 结果
     */
    public int insertAnswerRecord(AnswerRecord answerRecord);

    /**
     * 修改答题记录
     * 
     * @param answerRecord 答题记录
     * @return 结果
     */
    public int updateAnswerRecord(AnswerRecord answerRecord);

    /**
     * 批量删除答题记录
     * 
     * @param ids 需要删除的答题记录主键集合
     * @return 结果
     */
    public int deleteAnswerRecordByIds(Long[] ids);

    /**
     * 删除答题记录信息
     * 
     * @param id 答题记录主键
     * @return 结果
     */
    public int deleteAnswerRecordById(Long id);
}
