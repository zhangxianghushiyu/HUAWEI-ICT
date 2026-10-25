package com.ruoyi.platform.service;

import java.util.Date;

public interface AnswerRecordService {

    /**
     * 添加答题记录数据
     */
    void addAnswerRecord(Integer userId, Integer barrier, String name, String answer, String wrong,
                         String url, String write, Integer answerResult, Date date);
}
