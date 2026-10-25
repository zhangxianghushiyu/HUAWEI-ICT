package com.ruoyi.platform.service;

import com.ruoyi.platform.dto.FeedbackDTO;

public interface FeedbackService {

    /**
     * 填写反馈信息
     */
    void addFeedback(FeedbackDTO dto);
}
