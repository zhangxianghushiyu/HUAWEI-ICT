package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.ActivityRegistrationMapper;
import com.ruoyi.platform.domain.ActivityRegistration;
import com.ruoyi.platform.service.IActivityRegistrationService;

/**
 * 活动Service业务层处理
 * 
 * @author lhr
 * @date 2026-06-08
 */
@Service
public class ActivityRegistrationServiceImpl implements IActivityRegistrationService 
{
    @Autowired
    private ActivityRegistrationMapper activityRegistrationMapper;

    /**
     * 查询活动
     * 
     * @param id 活动主键
     * @return 活动
     */
    @Override
    public ActivityRegistration selectActivityRegistrationById(Long id)
    {
        return activityRegistrationMapper.selectActivityRegistrationById(id);
    }

    /**
     * 查询活动列表
     * 
     * @param activityRegistration 活动
     * @return 活动
     */
    @Override
    public List<ActivityRegistration> selectActivityRegistrationList(ActivityRegistration activityRegistration)
    {
        return activityRegistrationMapper.selectActivityRegistrationList(activityRegistration);
    }

    /**
     * 新增活动
     * 
     * @param activityRegistration 活动
     * @return 结果
     */
    @Override
    public int insertActivityRegistration(ActivityRegistration activityRegistration)
    {
        activityRegistration.setCreateTime(DateUtils.getNowDate());
        return activityRegistrationMapper.insertActivityRegistration(activityRegistration);
    }

    /**
     * 修改活动
     * 
     * @param activityRegistration 活动
     * @return 结果
     */
    @Override
    public int updateActivityRegistration(ActivityRegistration activityRegistration)
    {
        activityRegistration.setUpdateTime(DateUtils.getNowDate());
        return activityRegistrationMapper.updateActivityRegistration(activityRegistration);
    }

    /**
     * 批量删除活动
     * 
     * @param ids 需要删除的活动主键
     * @return 结果
     */
    @Override
    public int deleteActivityRegistrationByIds(Long[] ids)
    {
        return activityRegistrationMapper.deleteActivityRegistrationByIds(ids);
    }

    /**
     * 删除活动信息
     * 
     * @param id 活动主键
     * @return 结果
     */
    @Override
    public int deleteActivityRegistrationById(Long id)
    {
        return activityRegistrationMapper.deleteActivityRegistrationById(id);
    }
}
