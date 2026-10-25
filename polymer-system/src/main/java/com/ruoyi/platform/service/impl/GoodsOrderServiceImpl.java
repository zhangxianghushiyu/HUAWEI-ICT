package com.ruoyi.platform.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.GoodsOrder;
import com.ruoyi.platform.mapper.GoodsOrderMapper;
import com.ruoyi.platform.service.IGoodsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商城订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-18
 */
@Service
public class GoodsOrderServiceImpl implements IGoodsOrderService 
{
    @Autowired
    private GoodsOrderMapper goodsOrderMapper;

    /**
     * 查询商城订单
     * 
     * @param id 商城订单主键
     * @return 商城订单
     */
    @Override
    public GoodsOrder selectGoodsOrderById(Long id)
    {
        return goodsOrderMapper.selectGoodsOrderById(id);
    }

    /**
     * 查询商城订单列表
     * 
     * @param goodsOrder 商城订单
     * @return 商城订单
     */
    @Override
    public List<GoodsOrder> selectGoodsOrderList(GoodsOrder goodsOrder)
    {
        return goodsOrderMapper.selectGoodsOrderList(goodsOrder);
    }

    /**
     * 新增商城订单
     * 
     * @param goodsOrder 商城订单
     * @return 结果
     */
    @Override
    public int insertGoodsOrder(GoodsOrder goodsOrder)
    {
        goodsOrder.setCreateTime(DateUtils.getNowDate());
        return goodsOrderMapper.insertGoodsOrder(goodsOrder);
    }

    /**
     * 修改商城订单
     * 
     * @param goodsOrder 商城订单
     * @return 结果
     */
    @Override
    public int updateGoodsOrder(GoodsOrder goodsOrder)
    {
        goodsOrder.setUpdateTime(DateUtils.getNowDate());
        return goodsOrderMapper.updateGoodsOrder(goodsOrder);
    }

    /**
     * 批量删除商城订单
     * 
     * @param ids 需要删除的商城订单主键
     * @return 结果
     */
    @Override
    public int deleteGoodsOrderByIds(Long[] ids)
    {
        //接口更改为发货
        Long id = ids[0];
        GoodsOrder goodsOrder = goodsOrderMapper.selectGoodsOrderById(id);
        goodsOrder.setStatus(1);
        goodsOrderMapper.updateGoodsOrder(goodsOrder);
        return 1;
    }

    /**
     * 删除商城订单信息
     * 
     * @param id 商城订单主键
     * @return 结果
     */
    @Override
    public int deleteGoodsOrderById(Long id)
    {
        return goodsOrderMapper.deleteGoodsOrderById(id);
    }
}
