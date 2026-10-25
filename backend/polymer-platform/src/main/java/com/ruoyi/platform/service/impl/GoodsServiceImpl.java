package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.dto.GoodsGetDTO;
import com.ruoyi.platform.enums.BillSceneTypeEnum;
import com.ruoyi.platform.enums.UpStatusEnum;
import com.ruoyi.platform.mapper.GoodsDao;
import com.ruoyi.platform.mapper.GoodsOrderDao;
import com.ruoyi.platform.mapper.UserDao;
import com.ruoyi.platform.mapper.UserReceivingAddressDao;
import com.ruoyi.platform.service.BillGoldService;
import com.ruoyi.platform.service.GoodsService;
import com.ruoyi.platform.service.UserService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.SnowFlakeUtil;
import com.ruoyi.platform.vo.GoodsListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserReceivingAddressDao userReceivingAddressDao;
    @Autowired
    private GoodsOrderDao goodsOrderDao;
    @Autowired
    private BillGoldService billGoldService;

    @Override
    public List<GoodsListVO> getGoodsListVO(Integer goodsType, String goodsName) {
        return goodsDao.selectGoodsListVO(goodsType, goodsName);
    }

    @Transactional
    @Override
    public void buy(GoodsGetDTO dto) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        User user = userDao.selectByPrimaryKey(requestUserId);
        userService.checkUser(user);
        if (dto.getCount() < 1) {
            throw new ApiException("购买数量不能小于1");
        }
        //查询收货地址
        UserReceivingAddress address = userReceivingAddressDao.selectByPrimaryKey(dto.getAddressId());
        if (Objects.isNull(address)) {
            throw new ApiException("收货地址不存在");
        }
        if (!address.getUserId().equals(requestUserId)) {
            throw new ApiException("收货地址和您的信息不一致");
        }
        //查询商品
        Goods goods = goodsDao.selectByPrimaryKey(dto.getGoodsId());
        if (Objects.isNull(goods) || goods.getUpStatus().equals(UpStatusEnum.STATUS_0.getCode())) {
            throw new ApiException("商品信息不存在");
        }
        //查看用户金额是否足够进行购买
        //购买数量*单价=需要支付的总金额
        BigDecimal needPaySumMoney = goods.getLeafPrice().multiply(new BigDecimal(dto.getCount()));
        int result = user.getGoldCoin().compareTo(needPaySumMoney);
        if (result < 0) {
            throw new ApiException("金币不足，无法进行购买");
        }
        // 减金币，生成订单
        userDao.subGoldCoin(requestUserId, needPaySumMoney);
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setUserId(requestUserId);
        //生成一个订单号
        Date date = new Date();
        String orderNo = SnowFlakeUtil.getId().toString();
        goodsOrder.setOrderNo(orderNo);
        goodsOrder.setGoodsName(goods.getName());
        goodsOrder.setGoodsPrice(goods.getLeafPrice());
        goodsOrder.setGoodsUrl(goods.getUrl());
        goodsOrder.setPayMoney(needPaySumMoney);
        goodsOrder.setPayCount(dto.getCount());
        goodsOrder.setGetName(address.getName());
        goodsOrder.setGetPhone(address.getPhone());
        goodsOrder.setGetAddress(address.getAddress());
        goodsOrder.setGetCity(address.getCity());
        goodsOrder.setCreateTime(date);
        goodsOrderDao.insertSelective(goodsOrder);
        //添加金币记录
        billGoldService.addBillGold(requestUserId, BillSceneTypeEnum.SCENE_2.getCode(), "兑换" + goods.getName(), needPaySumMoney, date);
    }

    @Override
    public List<GoodsType> getGoodsTypeList() {
        return goodsDao.getGoodsTypeList();
    }
}
