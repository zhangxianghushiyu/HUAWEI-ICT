package com.ruoyi.platform.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.platform.dto.FeedbackListDTO;
import com.ruoyi.platform.vo.FeedbackListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.FeedbackMapper;
import com.ruoyi.platform.domain.Feedback;
import com.ruoyi.platform.service.IFeedbackService;

/**
 * 帮助反馈信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
@Service
public class FeedbackServiceImpl implements IFeedbackService 
{
    @Autowired
    private FeedbackMapper feedbackMapper;

    /**
     * 查询帮助反馈信息
     * 
     * @param id 帮助反馈信息主键
     * @return 帮助反馈信息
     */
    @Override
    public Feedback selectFeedbackById(Long id)
    {
        return feedbackMapper.selectFeedbackById(id);
    }

    /**
     * 查询帮助反馈信息列表
     * 
     * @param feedback 帮助反馈信息
     * @return 帮助反馈信息
     */
    @Override
    public List<Feedback> selectFeedbackList(Feedback feedback)
    {
        return feedbackMapper.selectFeedbackList(feedback);
    }

    /**
     * 新增帮助反馈信息
     * 
     * @param feedback 帮助反馈信息
     * @return 结果
     */
    @Override
    public int insertFeedback(Feedback feedback)
    {
        feedback.setCreateTime(DateUtils.getNowDate());
        return feedbackMapper.insertFeedback(feedback);
    }

    /**
     * 修改帮助反馈信息
     * 
     * @param feedback 帮助反馈信息
     * @return 结果
     */
    @Override
    public int updateFeedback(Feedback feedback)
    {
        feedback.setUpdateTime(DateUtils.getNowDate());
        return feedbackMapper.updateFeedback(feedback);
    }

    /**
     * 批量删除帮助反馈信息
     * 
     * @param ids 需要删除的帮助反馈信息主键
     * @return 结果
     */
    @Override
    public int deleteFeedbackByIds(Long[] ids)
    {
        return feedbackMapper.deleteFeedbackByIds(ids);
    }

    /**
     * 删除帮助反馈信息信息
     * 
     * @param id 帮助反馈信息主键
     * @return 结果
     */
    @Override
    public int deleteFeedbackById(Long id)
    {
        return feedbackMapper.deleteFeedbackById(id);
    }

    @Override
    public List<FeedbackListVO> getFeedbackListVO(FeedbackListDTO feedback) {
        List<FeedbackListVO> feedbackListVOS = feedbackMapper.selectFeedbackListVO(feedback);
        if (Objects.nonNull(feedbackListVOS)&&!feedbackListVOS.isEmpty()){
            for (FeedbackListVO feedbackListVO : feedbackListVOS) {
                if (!StringUtils.isEmpty(feedbackListVO.getImgUrl())){
                    List<String> stringList = Arrays.asList(feedbackListVO.getImgUrl().split(","));
                    feedbackListVO.setImgs(stringList);
                }
            }
        }
        return feedbackListVOS;
    }
}
