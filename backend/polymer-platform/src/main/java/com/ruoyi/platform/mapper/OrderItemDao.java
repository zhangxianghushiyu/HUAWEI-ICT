package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderItemDao {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);


    /**
     * 根据订单号查询，目前只查询一条
     * @return
     */
    OrderItem selectOrderItemByOrderNo(@Param("orderNo")String orderNo);


}
