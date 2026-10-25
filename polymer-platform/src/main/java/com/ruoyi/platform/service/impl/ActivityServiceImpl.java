package com.ruoyi.platform.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.platform.domain.ActivityRegistration;
import com.ruoyi.platform.domain.ActivitySignup;
import com.ruoyi.platform.dto.ActivitySignupDTO;
import com.ruoyi.platform.mapper.ActivityRegistrationDao;
import com.ruoyi.platform.mapper.ActivitySignupDao;
import com.ruoyi.platform.service.ActivityService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.ActivityDetailVO;
import com.ruoyi.platform.vo.ActivityListVO;
import com.ruoyi.platform.vo.MySignupListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 活动Service业务层处理
 */
@Slf4j
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRegistrationDao activityRegistrationDao;

    @Autowired
    private ActivitySignupDao activitySignupDao;

    @Autowired
    private HttpServletRequest request;

    /**
     * 查询活动列表
     */
    @Override
    public List<ActivityListVO> getActivityList() {
        List<ActivityListVO> list = activityRegistrationDao.selectActivityList(1);
        Integer userId = JwtUtil.getRequestUserId(request);
        
        // 设置是否已报名标记
        for (ActivityListVO vo : list) {
            if (userId != null) {
                ActivitySignup signup = activitySignupDao.selectByUserIdAndActivityId(userId, vo.getId());
                vo.setHasSignedUp(signup != null && signup.getStatus() != null && signup.getStatus() == 0);
            } else {
                vo.setHasSignedUp(false);
            }
        }
        
        return list;
    }

    /**
     * 查询活动详情
     */
    @Override
    public ActivityDetailVO getActivityDetail(Long activityId) {
        ActivityRegistration activity = activityRegistrationDao.selectByPrimaryKey(activityId);
        if (activity == null || (activity.getDelStatus() != null && activity.getDelStatus() == 0)) {
            throw new ServiceException("活动不存在");
        }

        ActivityDetailVO detailVO = new ActivityDetailVO();
        BeanUtils.copyProperties(activity, detailVO);

        // 统计报名人数
        int signupCount = activitySignupDao.countByActivityId(activityId);
        detailVO.setSignupCount(signupCount);

        // 检查当前用户是否已报名
        Integer userId = JwtUtil.getRequestUserId(request);
        if (userId != null) {
            ActivitySignup signup = activitySignupDao.selectByUserIdAndActivityId(userId, activityId);
            if (signup != null && signup.getStatus() == 0) {
                detailVO.setHasSignedUp(true);
                detailVO.setSignupTime(signup.getCreateTime());
            } else {
                detailVO.setHasSignedUp(false);
            }
        } else {
            detailVO.setHasSignedUp(false);
        }

        return detailVO;
    }

    /**
     * 报名活动
     */
    @Override
    @Transactional
    public void signupActivity(ActivitySignupDTO dto) {
        Integer userId = JwtUtil.getRequestUserId(request);
        Long activityId = dto.getActivityId();

        // 查询活动是否存在
        ActivityRegistration activity = activityRegistrationDao.selectByPrimaryKey(activityId);
        if (activity == null || (activity.getDelStatus() != null && activity.getDelStatus() == 0)) {
            throw new ServiceException("活动不存在");
        }

        // 检查活动状态
        Date now = new Date();
        if (activity.getStartTime() != null && now.before(activity.getStartTime())) {
            // 活动未开始，可以报名
        } else if (activity.getEndTime() != null && now.after(activity.getEndTime())) {
            throw new ServiceException("活动已结束，无法报名");
        }

        // 检查是否已报名
        ActivitySignup existSignup = activitySignupDao.selectByUserIdAndActivityId(userId, activityId);
        if (existSignup != null) {
            if (existSignup.getStatus() != null && existSignup.getStatus() == 0) {
                throw new ServiceException("您已经报名过该活动");
            } else {
                // 之前取消过，现在重新报名
                existSignup.setStatus(0L);
                existSignup.setUpdateTime(now);
                activitySignupDao.updateByPrimaryKeySelective(existSignup);
                log.info("用户{}重新报名活动{}", userId, activityId);
                return;
            }
        }

        // 创建报名记录
        ActivitySignup signup = new ActivitySignup();
        signup.setUserId(Long.valueOf(userId));
        signup.setActivityId(activityId);
        signup.setStatus(0L); // 0:已报名
        signup.setCreateTime(now);
        signup.setUpdateTime(now);
        
        activitySignupDao.insert(signup);
        log.info("用户{}报名活动{}成功", userId, activityId);
    }

    /**
     * 取消报名
     */
    @Override
    @Transactional
    public void cancelSignup(Long activityId) {
        Integer userId = JwtUtil.getRequestUserId(request);

        // 查询活动是否存在
        ActivityRegistration activity = activityRegistrationDao.selectByPrimaryKey(activityId);
        if (activity == null || (activity.getDelStatus() != null && activity.getDelStatus() == 0)) {
            throw new ServiceException("活动不存在");
        }

        // 检查是否已报名
        ActivitySignup signup = activitySignupDao.selectByUserIdAndActivityId(userId, activityId);
        if (signup == null || signup.getStatus() == null || signup.getStatus() != 0L) {
            throw new ServiceException("您还未报名该活动");
        }

        // 检查活动是否已开始
        Date now = new Date();
        if (activity.getStartTime() != null && now.after(activity.getStartTime())) {
            throw new ServiceException("活动已开始，无法取消报名");
        }

        // 更新报名状态为已取消
        signup.setStatus(1L); // 1:已取消
        signup.setUpdateTime(now);
        activitySignupDao.updateByPrimaryKeySelective(signup);
        
        log.info("用户{}取消报名活动{}", userId, activityId);
    }

    /**
     * 我的报名记录
     */
    @Override
    public List<MySignupListVO> getMySignupList() {
        Integer userId = JwtUtil.getRequestUserId(request);
        return activitySignupDao.selectMySignupList(userId);
    }
}
