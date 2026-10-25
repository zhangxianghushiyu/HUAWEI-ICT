package com.ruoyi.platform.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.Barrier;
import com.ruoyi.platform.mapper.BarrierMapper;
import com.ruoyi.platform.service.IBarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 关卡信息Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-07
 */
@Service
public class BarrierServiceImpl implements IBarrierService {
    @Autowired
    private BarrierMapper barrierMapper;

    /**
     * 查询关卡信息
     *
     * @param id 关卡信息主键
     * @return 关卡信息
     */
    @Override
    public Barrier selectBarrierById(Long id) {
        return barrierMapper.selectBarrierById(id);
    }

    /**
     * 查询关卡信息列表
     *
     * @param barrier 关卡信息
     * @return 关卡信息
     */
    @Override
    public List<Barrier> selectBarrierList(Barrier barrier) {
        return barrierMapper.selectBarrierList(barrier);
    }

    /**
     * 新增关卡信息
     *
     * @param barrier 关卡信息
     * @return 结果
     */
    @Override
    public int insertBarrier(Barrier barrier) {
        barrier.setCreateTime(DateUtils.getNowDate());
        return barrierMapper.insertBarrier(barrier);
    }

    /**
     * 修改关卡信息
     *
     * @param barrier 关卡信息
     * @return 结果
     */
    @Override
    public int updateBarrier(Barrier barrier) {
        barrier.setUpdateTime(DateUtils.getNowDate());
        return barrierMapper.updateBarrier(barrier);
    }

    /**
     * 批量删除关卡信息
     *
     * @param ids 需要删除的关卡信息主键
     * @return 结果
     */
    @Override
    public int deleteBarrierByIds(Long[] ids) {
        return barrierMapper.deleteBarrierByIds(ids);
    }

    /**
     * 删除关卡信息信息
     *
     * @param id 关卡信息主键
     * @return 结果
     */
    @Override
    public int deleteBarrierById(Long id) {
        return barrierMapper.deleteBarrierById(id);
    }

    @Override
    public Barrier selectBarrierByBarrier(Long barrier) {
        return barrierMapper.selectBarrierByBarrier(barrier);
    }
}
