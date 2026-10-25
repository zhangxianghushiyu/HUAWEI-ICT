package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.BillCash;
import com.ruoyi.platform.dto.BillCashListDTO;
import com.ruoyi.platform.vo.BillCashListVO;
import org.apache.ibatis.annotations.Param;

/**
 * 现金明细Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
public interface BillCashMapper 
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
     * 删除现金明细
     * 
     * @param id 现金明细主键
     * @return 结果
     */
    public int deleteBillCashById(Long id);

    /**
     * 批量删除现金明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBillCashByIds(Long[] ids);

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
    List<BillCashListVO> selectBillCashListJunior(BillCashListDTO dto);


    /**
     * 查询信息通过订单号
     * @param orderNo
     * @return
     */
    BillCash selectBillCashByOrderNo(@Param("orderNo")String orderNo);

    /**
     * 查询广告收入金额
     *
     * @return 广告回调集合
     */
    Float selectBillCashSumMoney(BillCashListDTO dto);

    /**
     * 查询广告收入金额(只查询自己的下级)
     *
     * @return 广告回调集合
     */
    Float selectBillCashSumMoneyJunior(BillCashListDTO dto);
}
