package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.GoodsOrder;

import java.util.List;

/**
 * 商城订单Service接口
 * 
 * @author ruoyi
 * @date 2025-03-18
 */
public interface IGoodsOrderService 
{
    /**
     * 查询商城订单
     * 
     * @param id 商城订单主键
     * @return 商城订单
     */
    public GoodsOrder selectGoodsOrderById(Long id);

    /**
     * 查询商城订单列表
     * 
     * @param goodsOrder 商城订单
     * @return 商城订单集合
     */
    public List<GoodsOrder> selectGoodsOrderList(GoodsOrder goodsOrder);

    /**
     * 新增商城订单
     * 
     * @param goodsOrder 商城订单
     * @return 结果
     */
    public int insertGoodsOrder(GoodsOrder goodsOrder);

    /**
     * 修改商城订单
     * 
     * @param goodsOrder 商城订单
     * @return 结果
     */
    public int updateGoodsOrder(GoodsOrder goodsOrder);

    /**
     * 批量删除商城订单
     * 
     * @param ids 需要删除的商城订单主键集合
     * @return 结果
     */
    public int deleteGoodsOrderByIds(Long[] ids);

    /**
     * 删除商城订单信息
     * 
     * @param id 商城订单主键
     * @return 结果
     */
    public int deleteGoodsOrderById(Long id);
}
