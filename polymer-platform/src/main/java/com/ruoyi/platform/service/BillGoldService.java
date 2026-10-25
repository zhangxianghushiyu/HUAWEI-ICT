package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.BillGoldListVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BillGoldService {


    /**
     * 添加金币账单信息
     */
    void addBillGold(Integer userId, Integer scene, String describe, BigDecimal money, Date createTime);


    /**
     * 查询我的金币明细账单
     */
    List<BillGoldListVO> getMyBillGoldListVO();
}
