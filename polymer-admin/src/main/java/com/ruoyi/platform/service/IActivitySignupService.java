package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.ActivitySignup;

/**
 * 报名记录Service接口
 * 
 * @author lhr
 * @date 2026-06-08
 */
public interface IActivitySignupService 
{
    /**
     * 查询报名记录
     * 
     * @param id 报名记录主键
     * @return 报名记录
     */
    public ActivitySignup selectActivitySignupById(Long id);

    /**
     * 查询报名记录列表
     * 
     * @param activitySignup 报名记录
     * @return 报名记录集合
     */
    public List<ActivitySignup> selectActivitySignupList(ActivitySignup activitySignup);

    /**
     * 新增报名记录
     * 
     * @param activitySignup 报名记录
     * @return 结果
     */
    public int insertActivitySignup(ActivitySignup activitySignup);

    /**
     * 修改报名记录
     * 
     * @param activitySignup 报名记录
     * @return 结果
     */
    public int updateActivitySignup(ActivitySignup activitySignup);

    /**
     * 批量删除报名记录
     * 
     * @param ids 需要删除的报名记录主键集合
     * @return 结果
     */
    public int deleteActivitySignupByIds(Long[] ids);

    /**
     * 删除报名记录信息
     * 
     * @param id 报名记录主键
     * @return 结果
     */
    public int deleteActivitySignupById(Long id);
}
