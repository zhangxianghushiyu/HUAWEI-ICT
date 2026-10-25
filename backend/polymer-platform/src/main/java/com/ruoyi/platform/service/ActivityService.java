package com.ruoyi.platform.service;

import com.ruoyi.platform.dto.ActivitySignupDTO;
import com.ruoyi.platform.vo.ActivityDetailVO;
import com.ruoyi.platform.vo.ActivityListVO;
import com.ruoyi.platform.vo.MySignupListVO;

import java.util.List;

/**
 * 活动Service接口
 */
public interface ActivityService {

    /**
     * 查询活动列表
     */
    List<ActivityListVO> getActivityList();

    /**
     * 查询活动详情
     */
    ActivityDetailVO getActivityDetail(Long activityId);

    /**
     * 报名活动
     */
    void signupActivity(ActivitySignupDTO dto);

    /**
     * 取消报名
     */
    void cancelSignup(Long activityId);

    /**
     * 我的报名记录
     */
    List<MySignupListVO> getMySignupList();
}
