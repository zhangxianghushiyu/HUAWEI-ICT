package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.TaKuCallback;

/**
 * taKu广告回调Service接口
 * 
 * @author ruoyi
 * @date 2025-02-27
 */
public interface ITaKuCallbackService 
{
    /**
     * 查询taKu广告回调
     * 
     * @param id taKu广告回调主键
     * @return taKu广告回调
     */
    public TaKuCallback selectTaKuCallbackById(Long id);

    /**
     * 查询taKu广告回调列表
     * 
     * @param taKuCallback taKu广告回调
     * @return taKu广告回调集合
     */
    public List<TaKuCallback> selectTaKuCallbackList(TaKuCallback taKuCallback);

    /**
     * 新增taKu广告回调
     * 
     * @param taKuCallback taKu广告回调
     * @return 结果
     */
    public int insertTaKuCallback(TaKuCallback taKuCallback);

    /**
     * 修改taKu广告回调
     * 
     * @param taKuCallback taKu广告回调
     * @return 结果
     */
    public int updateTaKuCallback(TaKuCallback taKuCallback);

    /**
     * 批量删除taKu广告回调
     * 
     * @param ids 需要删除的taKu广告回调主键集合
     * @return 结果
     */
    public int deleteTaKuCallbackByIds(Long[] ids);

    /**
     * 删除taKu广告回调信息
     * 
     * @param id taKu广告回调主键
     * @return 结果
     */
    public int deleteTaKuCallbackById(Long id);
}
