package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.DramaUnlock;

/**
 * 短剧解锁配置Service接口
 * 
 * @author ruoyi
 * @date 2025-01-17
 */
public interface IDramaUnlockService 
{
    /**
     * 查询短剧解锁配置
     * 
     * @param id 短剧解锁配置主键
     * @return 短剧解锁配置
     */
    public DramaUnlock selectDramaUnlockById(Long id);

    /**
     * 查询短剧解锁配置列表
     * 
     * @param dramaUnlock 短剧解锁配置
     * @return 短剧解锁配置集合
     */
    public List<DramaUnlock> selectDramaUnlockList(DramaUnlock dramaUnlock);

    /**
     * 新增短剧解锁配置
     * 
     * @param dramaUnlock 短剧解锁配置
     * @return 结果
     */
    public int insertDramaUnlock(DramaUnlock dramaUnlock);

    /**
     * 修改短剧解锁配置
     * 
     * @param dramaUnlock 短剧解锁配置
     * @return 结果
     */
    public int updateDramaUnlock(DramaUnlock dramaUnlock);

    /**
     * 批量删除短剧解锁配置
     * 
     * @param ids 需要删除的短剧解锁配置主键集合
     * @return 结果
     */
    public int deleteDramaUnlockByIds(Long[] ids);

    /**
     * 删除短剧解锁配置信息
     * 
     * @param id 短剧解锁配置主键
     * @return 结果
     */
    public int deleteDramaUnlockById(Long id);
}
