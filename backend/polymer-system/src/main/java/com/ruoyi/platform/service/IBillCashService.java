package com.ruoyi.platform.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.platform.domain.BillCash;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.dto.BillCashListDTO;
import com.ruoyi.platform.vo.BillCashListVO;

/**
 * 现金明细Service接口
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
public interface IBillCashService 
{
    /**
     * 查询现金明细
     * 
     * @param id 现金明细主键
     * @return 现金明细
     */
    public BillCash selectBillCashById(Long id);

    /**
     * 查询现金明细列表
     * 
     * @param billCash 现金明细
     * @return 现金明细集合
     */
    public List<BillCash> selectBillCashList(BillCash billCash);

    /**
     * 新增现金明细
     * 
     * @param billCash 现金明细
     * @return 结果
     */
    public int insertBillCash(BillCash billCash);

    /**
     * 修改现金明细
     * 
     * @param billCash 现金明细
     * @return 结果
     */
    public int updateBillCash(BillCash billCash);

    /**
     * 批量删除现金明细
     * 
     * @param ids 需要删除的现金明细主键集合
     * @return 结果
     */
    public int deleteBillCashByIds(Long[] ids);

    /**
     * 删除现金明细信息
     * 
     * @param id 现金明细主键
     * @return 结果
     */
    public int deleteBillCashById(Long id);

    /**
     * 现金明细
     * @param dto
     * @return
     */
    List<BillCashListVO> selectBillCashListVOInfo(BillCashListDTO dto);

    /**
     * 现金明细(只查询自己的下级)
     * @param dto
     * @return
     */
    List<BillCashListVO> getBillGoldListJunior(BillCashListDTO dto);



    /**
     * 同意
     *
     * @return 结果
     */
    public AjaxResult handleAgreeWith(Long[] ids) throws Exception;



    /**
     * 拒绝
     *
     * @return 结果
     */
    public int handleRefuse(Long[] ids);


    /**
     * 金币转现金
     */
    void goldCoinToCash(User user);

    /**
     * 统计
     */
    AjaxResult getStatisticsInfo(BillCashListDTO dto);

    /**
     * 统计只查询自己的下级
     */
    AjaxResult getStatisticsInfoJunior(BillCashListDTO dto);
}
