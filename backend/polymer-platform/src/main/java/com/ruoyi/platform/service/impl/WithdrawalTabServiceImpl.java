package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.mapper.WithdrawalTabDao;
import com.ruoyi.platform.service.WithdrawalTabService;
import com.ruoyi.platform.vo.WithdrawalTabInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WithdrawalTabServiceImpl implements WithdrawalTabService {

    @Autowired
    private WithdrawalTabDao withdrawalTabDao;

    @Override
    public WithdrawalTabInfoVO getWithdrawalTabVOInfo() {
        WithdrawalTabInfoVO vo=new WithdrawalTabInfoVO();
        vo.setWeiWithdrawalTab(withdrawalTabDao.selectWithdrawalTabVOInfo(1));
        vo.setAliWithdrawalTab(withdrawalTabDao.selectWithdrawalTabVOInfo(2));
        return vo;
    }
}
