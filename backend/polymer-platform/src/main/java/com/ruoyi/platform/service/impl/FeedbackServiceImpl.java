package com.ruoyi.platform.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.Feedback;
import com.ruoyi.platform.dto.FeedbackDTO;
import com.ruoyi.platform.mapper.FeedbackDao;
import com.ruoyi.platform.service.FeedbackService;
import com.ruoyi.platform.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;
    @Autowired
    private HttpServletRequest request;

    @Override
    public void addFeedback(FeedbackDTO dto) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        Feedback feedback = new Feedback();
        feedback.setContent(dto.getContent());
        feedback.setUserId(requestUserId);
        feedback.setCreateTime(new Date());
        feedback.setPhone(dto.getPhone());
        if (Objects.nonNull(dto.getImgUrls()) && !dto.getImgUrls().isEmpty()) {
            if (dto.getImgUrls().size() > 5) {
                throw new ApiException("上传图片最多5张");
            }
            String join = StrUtil.join(", ", dto.getImgUrls());
            feedback.setImgUrl(join);
        }
        feedbackDao.insertSelective(feedback);
    }
}
