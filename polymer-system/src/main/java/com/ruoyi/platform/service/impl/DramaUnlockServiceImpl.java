package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.mapper.DramaDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.DramaUnlockMapper;
import com.ruoyi.platform.domain.DramaUnlock;
import com.ruoyi.platform.service.IDramaUnlockService;

/**
 * 短剧解锁配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-17
 */
@Service
public class DramaUnlockServiceImpl implements IDramaUnlockService 
{
    @Autowired
    private DramaUnlockMapper dramaUnlockMapper;
    @Autowired
    private DramaDetailsMapper dramaDetailsMapper;

    /**
     * 查询短剧解锁配置
     * 
     * @param id 短剧解锁配置主键
     * @return 短剧解锁配置
     */
    @Override
    public DramaUnlock selectDramaUnlockById(Long id)
    {
        return dramaUnlockMapper.selectDramaUnlockById(id);
    }

    /**
     * 查询短剧解锁配置列表
     * 
     * @param dramaUnlock 短剧解锁配置
     * @return 短剧解锁配置
     */
    @Override
    public List<DramaUnlock> selectDramaUnlockList(DramaUnlock dramaUnlock)
    {
        return dramaUnlockMapper.selectDramaUnlockList(dramaUnlock);
    }

    /**
     * 新增短剧解锁配置
     * 
     * @param dramaUnlock 短剧解锁配置
     * @return 结果
     */
    @Override
    public int insertDramaUnlock(DramaUnlock dramaUnlock)
    {
        dramaUnlock.setCreateTime(DateUtils.getNowDate());
        return dramaUnlockMapper.insertDramaUnlock(dramaUnlock);
    }

    /**
     * 修改短剧解锁配置
     * 
     * @param dramaUnlock 短剧解锁配置
     * @return 结果
     */
    @Override
    public int updateDramaUnlock(DramaUnlock dramaUnlock)
    {
        DramaUnlock old =  dramaUnlockMapper.selectDramaUnlockById(dramaUnlock.getId());
        dramaUnlock.setUpdateTime(DateUtils.getNowDate());
        if(old.getFreeNum() > dramaUnlock.getFreeNum()){
            dramaDetailsMapper.updateIsPriceStatusByFreeNum(1, dramaUnlock.getFreeNum()+1, old.getFreeNum());
        } else if (old.getFreeNum() < dramaUnlock.getFreeNum()) {
            dramaDetailsMapper.updateIsPriceStatusByFreeNum(0, old.getFreeNum()+1, dramaUnlock.getFreeNum());
        }
        return dramaUnlockMapper.updateDramaUnlock(dramaUnlock);
    }

    /**
     * 批量删除短剧解锁配置
     * 
     * @param ids 需要删除的短剧解锁配置主键
     * @return 结果
     */
    @Override
    public int deleteDramaUnlockByIds(Long[] ids)
    {
        return dramaUnlockMapper.deleteDramaUnlockByIds(ids);
    }

    /**
     * 删除短剧解锁配置信息
     * 
     * @param id 短剧解锁配置主键
     * @return 结果
     */
    @Override
    public int deleteDramaUnlockById(Long id)
    {
        return dramaUnlockMapper.deleteDramaUnlockById(id);
    }
}
