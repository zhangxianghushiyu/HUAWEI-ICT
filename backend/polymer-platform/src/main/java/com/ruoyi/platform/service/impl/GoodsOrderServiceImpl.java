package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.GoodsOrder;
import com.ruoyi.platform.mapper.GoodsOrderDao;
import com.ruoyi.platform.service.GoodsOrderService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.GoodsOrderListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
public class GoodsOrderServiceImpl implements GoodsOrderService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private GoodsOrderDao goodsOrderDao;

    @Override
    public List<GoodsOrderListVO> getGoodsOrderListVO() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        return goodsOrderDao.selectGoodsOrderListVO(requestUserId);
    }


    @Override
    public void takeOver(Integer id) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        GoodsOrder goodsOrder = goodsOrderDao.selectByPrimaryKey(id);
        if (Objects.isNull(goodsOrder)) {
            throw new ApiException("订单信息不存在！");
        }
        if (!goodsOrder.getUserId().equals(requestUserId)){
            throw new ApiException("操作权限不足");
        }
        if (goodsOrder.getStatus()!=1){
            throw new ApiException("订单状态不允许确认收货！");
        }
        goodsOrder.setStatus(2);
        goodsOrderDao.updateByPrimaryKeySelective(goodsOrder);
    }
}
