package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.dto.AdCallbackDTO;
import com.ruoyi.platform.vo.AdCallbackVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.AdCallbackMapper;
import com.ruoyi.platform.domain.AdCallback;
import com.ruoyi.platform.service.IAdCallbackService;

/**
 * 广告回调Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-08
 */
@Service
public class AdCallbackServiceImpl implements IAdCallbackService 
{
    @Autowired
    private AdCallbackMapper adCallbackMapper;

    /**
     * 查询广告回调
     * 
     * @param id 广告回调主键
     * @return 广告回调
     */
    @Override
    public AdCallback selectAdCallbackById(Long id)
    {
        return adCallbackMapper.selectAdCallbackById(id);
    }

    /**
     * 查询广告回调列表
     * 
     * @param adCallback 广告回调
     * @return 广告回调
     */
    @Override
    public List<AdCallback> selectAdCallbackList(AdCallback adCallback)
    {
        return adCallbackMapper.selectAdCallbackList(adCallback);
    }
    /**
     * 查询广告回调列表
     *
     * @param adCallback 广告回调
     * @return 广告回调
     */
    @Override
    public List<AdCallbackVO> selectAdCallbackListInfo(AdCallbackDTO adCallback)
    {
        return adCallbackMapper.selectAdCallbackListInfo(adCallback);
    }

    /**
     * 新增广告回调
     * 
     * @param adCallback 广告回调
     * @return 结果
     */
    @Override
    public int insertAdCallback(AdCallback adCallback)
    {
        adCallback.setCreateTime(DateUtils.getNowDate());
        return adCallbackMapper.insertAdCallback(adCallback);
    }

    /**
     * 修改广告回调
     * 
     * @param adCallback 广告回调
     * @return 结果
     */
    @Override
    public int updateAdCallback(AdCallback adCallback)
    {
        adCallback.setUpdateTime(DateUtils.getNowDate());
        return adCallbackMapper.updateAdCallback(adCallback);
    }

    /**
     * 批量删除广告回调
     * 
     * @param ids 需要删除的广告回调主键
     * @return 结果
     */
    @Override
    public int deleteAdCallbackByIds(Long[] ids)
    {
        return adCallbackMapper.deleteAdCallbackByIds(ids);
    }

    /**
     * 删除广告回调信息
     * 
     * @param id 广告回调主键
     * @return 结果
     */
    @Override
    public int deleteAdCallbackById(Long id)
    {
        return adCallbackMapper.deleteAdCallbackById(id);
    }
}
