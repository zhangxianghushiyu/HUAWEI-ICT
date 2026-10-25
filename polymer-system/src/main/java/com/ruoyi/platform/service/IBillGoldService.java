package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.BillGold;
import com.ruoyi.platform.dto.BillGoldListDTO;
import com.ruoyi.platform.vo.BillGoldListVO;

/**
 * 金币明细Service接口
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
public interface IBillGoldService 
{
    /**
     * 查询金币明细
     * 
     * @param id 金币明细主键
     * @return 金币明细
     */
    public BillGold selectBillGoldById(Long id);

    /**
     * 查询金币明细列表
     * 
     * @param billGold 金币明细
     * @return 金币明细集合
     */
    public List<BillGold> selectBillGoldList(BillGold billGold);

    /**
     * 新增金币明细
     * 
     * @param billGold 金币明细
     * @return 结果
     */
    public int insertBillGold(BillGold billGold);

    /**
     * 修改金币明细
     * 
     * @param billGold 金币明细
     * @return 结果
     */
    public int updateBillGold(BillGold billGold);

    /**
     * 批量删除金币明细
     * 
     * @param ids 需要删除的金币明细主键集合
     * @return 结果
     */
    public int deleteBillGoldByIds(Long[] ids);

    /**
     * 删除金币明细信息
     * 
     * @param id 金币明细主键
     * @return 结果
     */
    public int deleteBillGoldById(Long id);


    /**
     * 账单详细
     * @return
     */
    List<BillGoldListVO> getBillGoldListVOInfo(BillGoldListDTO dto);


    /**
     * 账单详细（查询自己的用户下级）
     * @return
     */
    List<BillGoldListVO> getBillGoldListJunior(BillGoldListDTO dto);
}
