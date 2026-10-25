package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.Feedback;
import com.ruoyi.platform.dto.FeedbackListDTO;
import com.ruoyi.platform.vo.FeedbackListVO;

/**
 * 帮助反馈信息Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
public interface FeedbackMapper 
{
    /**
     * 查询帮助反馈信息
     * 
     * @param id 帮助反馈信息主键
     * @return 帮助反馈信息
     */
    public Feedback selectFeedbackById(Long id);

    /**
     * 查询帮助反馈信息列表
     * 
     * @param feedback 帮助反馈信息
     * @return 帮助反馈信息集合
     */
    public List<Feedback> selectFeedbackList(Feedback feedback);

    /**
     * 新增帮助反馈信息
     * 
     * @param feedback 帮助反馈信息
     * @return 结果
     */
    public int insertFeedback(Feedback feedback);

    /**
     * 修改帮助反馈信息
     * 
     * @param feedback 帮助反馈信息
     * @return 结果
     */
    public int updateFeedback(Feedback feedback);

    /**
     * 删除帮助反馈信息
     * 
     * @param id 帮助反馈信息主键
     * @return 结果
     */
    public int deleteFeedbackById(Long id);

    /**
     * 批量删除帮助反馈信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFeedbackByIds(Long[] ids);


    /**
     * 反馈信息
     * @param feedback
     * @return
     */
    List<FeedbackListVO> selectFeedbackListVO(FeedbackListDTO feedback);
}
