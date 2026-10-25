package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TreasureBoxMapper;
import com.ruoyi.platform.domain.TreasureBox;
import com.ruoyi.platform.service.ITreasureBoxService;

/**
 * 宝箱配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-12-02
 */
@Service
public class TreasureBoxServiceImpl implements ITreasureBoxService 
{
    @Autowired
    private TreasureBoxMapper treasureBoxMapper;

    /**
     * 查询宝箱配置
     * 
     * @param id 宝箱配置主键
     * @return 宝箱配置
     */
    @Override
    public TreasureBox selectTreasureBoxById(Long id)
    {
        return treasureBoxMapper.selectTreasureBoxById(id);
    }

    /**
     * 查询宝箱配置列表
     * 
     * @param treasureBox 宝箱配置
     * @return 宝箱配置
     */
    @Override
    public List<TreasureBox> selectTreasureBoxList(TreasureBox treasureBox)
    {
        return treasureBoxMapper.selectTreasureBoxList(treasureBox);
    }

    /**
     * 新增宝箱配置
     * 
     * @param treasureBox 宝箱配置
     * @return 结果
     */
    @Override
    public int insertTreasureBox(TreasureBox treasureBox)
    {
        treasureBox.setCreateTime(DateUtils.getNowDate());
        return treasureBoxMapper.insertTreasureBox(treasureBox);
    }

    /**
     * 修改宝箱配置
     * 
     * @param treasureBox 宝箱配置
     * @return 结果
     */
    @Override
    public int updateTreasureBox(TreasureBox treasureBox)
    {
        treasureBox.setUpdateTime(DateUtils.getNowDate());
        return treasureBoxMapper.updateTreasureBox(treasureBox);
    }

    /**
     * 批量删除宝箱配置
     * 
     * @param ids 需要删除的宝箱配置主键
     * @return 结果
     */
    @Override
    public int deleteTreasureBoxByIds(Long[] ids)
    {
        return treasureBoxMapper.deleteTreasureBoxByIds(ids);
    }

    /**
     * 删除宝箱配置信息
     * 
     * @param id 宝箱配置主键
     * @return 结果
     */
    @Override
    public int deleteTreasureBoxById(Long id)
    {
        return treasureBoxMapper.deleteTreasureBoxById(id);
    }
}
