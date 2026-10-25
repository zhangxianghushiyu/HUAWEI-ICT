package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.ActivitySignupMapper;
import com.ruoyi.platform.domain.ActivitySignup;
import com.ruoyi.platform.service.IActivitySignupService;

/**
 * 报名记录Service业务层处理
 * 
 * @author lhr
 * @date 2026-06-08
 */
@Service
public class ActivitySignupServiceImpl implements IActivitySignupService 
{
    @Autowired
    private ActivitySignupMapper activitySignupMapper;

    /**
     * 查询报名记录
     * 
     * @param id 报名记录主键
     * @return 报名记录
     */
    @Override
    public ActivitySignup selectActivitySignupById(Long id)
    {
        return activitySignupMapper.selectActivitySignupById(id);
    }

    /**
     * 查询报名记录列表
     * 
     * @param activitySignup 报名记录
     * @return 报名记录
     */
    @Override
    public List<ActivitySignup> selectActivitySignupList(ActivitySignup activitySignup)
    {
        return activitySignupMapper.selectActivitySignupList(activitySignup);
    }

    /**
     * 新增报名记录
     * 
     * @param activitySignup 报名记录
     * @return 结果
     */
    @Override
    public int insertActivitySignup(ActivitySignup activitySignup)
    {
        activitySignup.setCreateTime(DateUtils.getNowDate());
        return activitySignupMapper.insertActivitySignup(activitySignup);
    }

    /**
     * 修改报名记录
     * 
     * @param activitySignup 报名记录
     * @return 结果
     */
    @Override
    public int updateActivitySignup(ActivitySignup activitySignup)
    {
        activitySignup.setUpdateTime(DateUtils.getNowDate());
        return activitySignupMapper.updateActivitySignup(activitySignup);
    }

    /**
     * 批量删除报名记录
     * 
     * @param ids 需要删除的报名记录主键
     * @return 结果
     */
    @Override
    public int deleteActivitySignupByIds(Long[] ids)
    {
        return activitySignupMapper.deleteActivitySignupByIds(ids);
    }

    /**
     * 删除报名记录信息
     * 
     * @param id 报名记录主键
     * @return 结果
     */
    @Override
    public int deleteActivitySignupById(Long id)
    {
        return activitySignupMapper.deleteActivitySignupById(id);
    }
}
