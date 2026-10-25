package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.BillCash;
import com.ruoyi.platform.vo.BillCashListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BillCashDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BillCash record);

    int insertSelective(BillCash record);

    BillCash selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillCash record);

    int updateByPrimaryKey(BillCash record);


    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    BillCash selectBillCashByOrderNo(@Param("orderNo")String orderNo);


    /**
     * 查询现金账单
     * @return
     */
    List<BillCashListVO> selectMyBillCashListVO(@Param("userId")Integer userId);

    /**
     *查看今日提现次数
     */
    Integer selectRecentlyBillCash(@Param("userId")Integer userId);
}