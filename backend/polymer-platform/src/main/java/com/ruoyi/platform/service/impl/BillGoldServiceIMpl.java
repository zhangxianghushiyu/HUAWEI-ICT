package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.domain.BillGold;
import com.ruoyi.platform.mapper.BillGoldDao;
import com.ruoyi.platform.service.BillGoldService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.BillGoldListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BillGoldServiceIMpl implements BillGoldService {

    @Autowired
    private BillGoldDao billGoldDao;
    @Autowired
    private HttpServletRequest request;

    @Override
    public void addBillGold(Integer userId, Integer scene, String describe, BigDecimal money, Date createTime) {
        BillGold billGold=new BillGold();
        billGold.setUserId(userId);
        billGold.setScene(scene);
        billGold.setDescribe(describe);
        billGold.setMoney(money);
        billGold.setCreateTime(createTime);
        billGoldDao.insertSelective(billGold);
    }


    @Override
    public List<BillGoldListVO> getMyBillGoldListVO() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        return billGoldDao.selectMyBillGoldListVO(requestUserId);
    }
}
