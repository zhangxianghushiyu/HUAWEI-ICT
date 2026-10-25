package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.AdCallback;
import com.ruoyi.platform.dto.AdCallbackDTO;
import com.ruoyi.platform.vo.AdCallbackVO;

/**
 * 广告回调Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-08
 */
public interface AdCallbackMapper 
{
    /**
     * 查询广告回调
     * 
     * @param id 广告回调主键
     * @return 广告回调
     */
    public AdCallback selectAdCallbackById(Long id);

    /**
     * 查询广告回调列表
     * 
     * @param adCallback 广告回调
     * @return 广告回调集合
     */
    public List<AdCallback> selectAdCallbackList(AdCallback adCallback);

    /**
     * 查询广告回调列表
     *
     * @param adCallback 广告回调
     * @return 广告回调集合
     */
    public List<AdCallbackVO> selectAdCallbackListInfo(AdCallbackDTO adCallback);

    /**
     * 新增广告回调
     * 
     * @param adCallback 广告回调
     * @return 结果
     */
    public int insertAdCallback(AdCallback adCallback);

    /**
     * 修改广告回调
     * 
     * @param adCallback 广告回调
     * @return 结果
     */
    public int updateAdCallback(AdCallback adCallback);

    /**
     * 删除广告回调
     * 
     * @param id 广告回调主键
     * @return 结果
     */
    public int deleteAdCallbackById(Long id);

    /**
     * 批量删除广告回调
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAdCallbackByIds(Long[] ids);
}
