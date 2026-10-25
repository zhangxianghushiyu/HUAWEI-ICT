package com.ruoyi.platform.service;


import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.BillCashListVO;
import com.ruoyi.platform.vo.WithdrawalReturnVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BillCashService {


    /**
     * 添加现金账单信息
     */
    void addBillCash(Integer method, Integer type, String orderNo, Integer userId, Integer scene, String describe,
                     BigDecimal money, Date createTime, Integer withdrawalTabId,
                     Integer autoAccount, Integer take, Integer status, Date payTime,
                     String packageInfo,String zfbName,String zfbUserName);


    /**
     * 申请提现
     *
     * @param withdrawalTabId
     */
    R<WithdrawalReturnVO> applyWithdrawal(Integer withdrawalTabId, BigDecimal price, Integer withdrawalType) throws Exception;




    /**
     * 现金账单明细
     *
     * @return
     */
    List<BillCashListVO> getMyBillCashListVO();
}
