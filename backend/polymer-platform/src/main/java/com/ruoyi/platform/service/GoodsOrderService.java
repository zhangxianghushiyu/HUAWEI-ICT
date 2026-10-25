package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.GoodsOrderListVO;

import java.util.List;

public interface GoodsOrderService {


    /**
     * 获取订单信息
     */
    List<GoodsOrderListVO> getGoodsOrderListVO();


    /**
     * 确认收货
     */
    void takeOver(Integer id);
}
