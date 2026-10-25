package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.domain.AnswerRecord;
import com.ruoyi.platform.mapper.AnswerRecordDao;
import com.ruoyi.platform.service.AnswerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class AnswerRecordServiceImpl implements AnswerRecordService {

    @Autowired
    private AnswerRecordDao answerRecordDao;

    @Override
    public void addAnswerRecord(Integer userId, Integer barrier, String name, String answer, String wrong, String url, String write,
                                Integer answerResult, Date date) {
        AnswerRecord answerRecord = new AnswerRecord();
        answerRecord.setUserId(userId);
        answerRecord.setBarrier(barrier);
        answerRecord.setName(name);
        answerRecord.setAnswer(answer);
        answerRecord.setWrong(wrong);
        answerRecord.setUrl(url);
        answerRecord.setWrite(write);
        answerRecord.setAnswerResult(answerResult);
        answerRecord.setCreateTime(date);
        answerRecordDao.insertSelective(answerRecord);
    }
}
