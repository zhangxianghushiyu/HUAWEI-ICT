package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.dto.BillGoldListDTO;
import com.ruoyi.platform.vo.BillGoldListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.BillGoldMapper;
import com.ruoyi.platform.domain.BillGold;
import com.ruoyi.platform.service.IBillGoldService;

/**
 * 金币明细Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
@Service
public class BillGoldServiceImpl implements IBillGoldService 
{
    @Autowired
    private BillGoldMapper billGoldMapper;

    /**
     * 查询金币明细
     * 
     * @param id 金币明细主键
     * @return 金币明细
     */
    @Override
    public BillGold selectBillGoldById(Long id)
    {
        return billGoldMapper.selectBillGoldById(id);
    }

    /**
     * 查询金币明细列表
     * 
     * @param billGold 金币明细
     * @return 金币明细
     */
    @Override
    public List<BillGold> selectBillGoldList(BillGold billGold)
    {
        return billGoldMapper.selectBillGoldList(billGold);
    }

    /**
     * 新增金币明细
     * 
     * @param billGold 金币明细
     * @return 结果
     */
    @Override
    public int insertBillGold(BillGold billGold)
    {
        billGold.setCreateTime(DateUtils.getNowDate());
        return billGoldMapper.insertBillGold(billGold);
    }

    /**
     * 修改金币明细
     * 
     * @param billGold 金币明细
     * @return 结果
     */
    @Override
    public int updateBillGold(BillGold billGold)
    {
        billGold.setUpdateTime(DateUtils.getNowDate());
        return billGoldMapper.updateBillGold(billGold);
    }

    /**
     * 批量删除金币明细
     * 
     * @param ids 需要删除的金币明细主键
     * @return 结果
     */
    @Override
    public int deleteBillGoldByIds(Long[] ids)
    {
        return billGoldMapper.deleteBillGoldByIds(ids);
    }

    /**
     * 删除金币明细信息
     * 
     * @param id 金币明细主键
     * @return 结果
     */
    @Override
    public int deleteBillGoldById(Long id)
    {
        return billGoldMapper.deleteBillGoldById(id);
    }

    @Override
    public List<BillGoldListVO> getBillGoldListVOInfo(BillGoldListDTO dto) {
        return billGoldMapper.selectBillGoldListVOInfo(dto);
    }


    @Override
    public List<BillGoldListVO> getBillGoldListJunior(BillGoldListDTO dto) {
        return billGoldMapper.selectBillGoldListJunior(dto);
    }
}
