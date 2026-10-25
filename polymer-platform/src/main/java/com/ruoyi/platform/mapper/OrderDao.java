package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.Order;
import com.ruoyi.platform.vo.BuyVipListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 根据订单号查询订单
     */
    Order selectOrderByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 查询购买会员记录
     */
    List<BuyVipListVO> selectBuyVipListVO(@Param("userId") Integer userId);
}
