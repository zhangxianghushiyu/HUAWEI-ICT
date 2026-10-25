package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.TreasureBox;

/**
 * 宝箱配置Mapper接口
 * 
 * @author ruoyi
 * @date 2024-12-02
 */
public interface TreasureBoxMapper 
{
    /**
     * 查询宝箱配置
     * 
     * @param id 宝箱配置主键
     * @return 宝箱配置
     */
    public TreasureBox selectTreasureBoxById(Long id);

    /**
     * 查询宝箱配置列表
     * 
     * @param treasureBox 宝箱配置
     * @return 宝箱配置集合
     */
    public List<TreasureBox> selectTreasureBoxList(TreasureBox treasureBox);

    /**
     * 新增宝箱配置
     * 
     * @param treasureBox 宝箱配置
     * @return 结果
     */
    public int insertTreasureBox(TreasureBox treasureBox);

    /**
     * 修改宝箱配置
     * 
     * @param treasureBox 宝箱配置
     * @return 结果
     */
    public int updateTreasureBox(TreasureBox treasureBox);

    /**
     * 删除宝箱配置
     * 
     * @param id 宝箱配置主键
     * @return 结果
     */
    public int deleteTreasureBoxById(Long id);

    /**
     * 批量删除宝箱配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTreasureBoxByIds(Long[] ids);
}
