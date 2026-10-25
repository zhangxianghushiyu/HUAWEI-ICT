package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.ActivityRegistration;

/**
 * 活动Service接口
 * 
 * @author lhr
 * @date 2026-06-08
 */
public interface IActivityRegistrationService 
{
    /**
     * 查询活动
     * 
     * @param id 活动主键
     * @return 活动
     */
    public ActivityRegistration selectActivityRegistrationById(Long id);

    /**
     * 查询活动列表
     * 
     * @param activityRegistration 活动
     * @return 活动集合
     */
    public List<ActivityRegistration> selectActivityRegistrationList(ActivityRegistration activityRegistration);

    /**
     * 新增活动
     * 
     * @param activityRegistration 活动
     * @return 结果
     */
    public int insertActivityRegistration(ActivityRegistration activityRegistration);

    /**
     * 修改活动
     * 
     * @param activityRegistration 活动
     * @return 结果
     */
    public int updateActivityRegistration(ActivityRegistration activityRegistration);

    /**
     * 批量删除活动
     * 
     * @param ids 需要删除的活动主键集合
     * @return 结果
     */
    public int deleteActivityRegistrationByIds(Long[] ids);

    /**
     * 删除活动信息
     * 
     * @param id 活动主键
     * @return 结果
     */
    public int deleteActivityRegistrationById(Long id);
}
