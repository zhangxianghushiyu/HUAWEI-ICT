package com.ruoyi.platform.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.domain.wx.TransferBillsResult;
import com.ruoyi.platform.enums.*;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.*;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.utils.SnowFlakeUtil;
import com.ruoyi.platform.vo.BillCashListVO;
import com.ruoyi.platform.vo.WithdrawalReturnVO;
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
public class BillCashServiceImpl implements BillCashService {

    @Autowired
    private BillCashDao billCashDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private WithdrawalTabDao withdrawalTabDao;
    @Autowired
    private WxService wxService;
    @Autowired
    private AliService aliService;
    @Autowired
    private WithdrawalUserDao withdrawalUserDao;
    @Autowired
    private WithdrawalIntervalDao withdrawalIntervalDao;
    @Autowired
    private ParamsConfigService paramsConfigService;

    @Override
    public void addBillCash(Integer method, Integer type, String orderNo, Integer userId, Integer scene, String describe,
                            BigDecimal money, Date createTime, Integer withdrawalTabId, Integer autoAccount, Integer take,
                            Integer status, Date payTime, String packageInfo, String zfbName, String zfbUserName) {
        BillCash billCash = new BillCash();
        billCash.setMethod(Objects.nonNull(method) ? method : null);
        billCash.setType(type);
        billCash.setOrderNo(Objects.nonNull(orderNo) ? orderNo : null);
        billCash.setUserId(userId);
        billCash.setScene(scene);
        billCash.setDescribe(describe);
        billCash.setMoney(money);
        billCash.setStatus(Objects.nonNull(status) ? status : null);
        billCash.setWithdrawalTabId(Objects.nonNull(withdrawalTabId) ? withdrawalTabId : null);
        billCash.setAutoAccount(Objects.nonNull(autoAccount) ? autoAccount : null);
        billCash.setTake(take);
        billCash.setCreateTime(createTime);
        billCash.setPayTime(Objects.nonNull(payTime) ? payTime : null);
        billCash.setPackageInfo(Objects.nonNull(packageInfo) ? packageInfo : null);
        billCash.setZfbName(StrUtil.isNotEmpty(zfbName) ? zfbName : null);
        billCash.setZfbUserName(StrUtil.isNotEmpty(zfbUserName) ? zfbUserName : null);
        billCashDao.insertSelective(billCash);
    }


    @Override
    @Transactional
    public R<WithdrawalReturnVO> applyWithdrawal(Integer withdrawalTabId, BigDecimal price, Integer withdrawalType) throws Exception {
        WithdrawalReturnVO vo = new WithdrawalReturnVO();
        Date date = new Date();
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        BigDecimal amount;
        Integer autoAccount = 0;
        //查看选项卡是否存在
        if(withdrawalTabId == 0){
            amount = price;
            List<ParamsConfig> paramsList = paramsConfigService.getParamsList(16);
            int result = amount.compareTo(new BigDecimal(paramsList.get(0).getValue()));
            if (result < 0) {
                autoAccount = 1;
            }
        }else{
            WithdrawalTab withdrawalTab = withdrawalTabDao.selectByPrimaryKey(withdrawalTabId);
            if (Objects.isNull(withdrawalTab)) {
                throw new ApiException("提现选项卡信息不存在");
            }
            amount = withdrawalTab.getAmount();
            autoAccount = withdrawalTab.getAutoAccount();
            withdrawalType=withdrawalTab.getType();
        }
        //查询用户信息
        User user = userDao.selectByPrimaryKey(requestUserId);
        userService.checkUser(user);
        vo.setType(withdrawalType);
        //查看选项卡类型
        WithdrawalUser withdrawalUser = null;
        if (withdrawalType.equals(WithdrawalTabEnum.WEI.getCode())) {
            //微信
            withdrawalUser = withdrawalUserDao.selectWithdrawalUserByTypeAndUserId(WithdrawalUserEnum.WEI.getCode(),
                    requestUserId);
        } else if (withdrawalType.equals(WithdrawalTabEnum.ALI.getCode())) {
            //支付宝
            withdrawalUser = withdrawalUserDao.selectWithdrawalUserByTypeAndUserId(WithdrawalUserEnum.ALI.getCode(),
                    requestUserId);
        }
        if (Objects.isNull(withdrawalUser)) {
            throw new ApiException("没有绑定提现账号信息");
        }

        //查看金额是否足够
        BigDecimal cash = user.getCash();
        int result = cash.compareTo(amount);
        if (result < 0) {
            throw new ApiException("金额不足，无法提现");
        }
        //查看今日提现次数
        Integer count = billCashDao.selectRecentlyBillCash(requestUserId);
        //查看设置的当天提现次数
        WithdrawalInterval withdrawalInterval = withdrawalIntervalDao.selectWithdrawalIntervalInfo();
        if (withdrawalInterval.getDay() == 0) {
            throw new ApiException("无法提现，具体原因请联系客服!");
        }
        if (count >= withdrawalInterval.getDay()) {
            throw new ApiException("申请提现失败，今日提现次数已上限");
        }
        //生成一个订单号
        String orderNo = SnowFlakeUtil.getId().toString();
        //查看选项卡是不是直接到账还是后台审核
        if (autoAccount == 0) {
            //查看是不是微信提现
            if (withdrawalType.equals(WithdrawalTabEnum.WEI.getCode())) {
                //处理微信提现信息
                //添加数据，未审核状态
                addBillCash(withdrawalType, BillCashTypeEnum.TYPE_2.getCode(), orderNo, requestUserId, BillSceneTypeEnum.SCENE_2.getCode(), "申请提现",
                        amount, date, withdrawalTabId, autoAccount, BillCashTakeEnum.TYPE_2.getCode(),
                        BillCashStatusEnum.STATUS_0.getCode(), null, null, null, null);
            } else {
                //支付宝提现
                //添加数据，未审核状态
                addBillCash(withdrawalType, BillCashTypeEnum.TYPE_2.getCode(), orderNo, requestUserId, BillSceneTypeEnum.SCENE_2.getCode(), "申请提现",
                        amount, date, withdrawalTabId, autoAccount, BillCashTakeEnum.TYPE_2.getCode(),
                        BillCashStatusEnum.STATUS_0.getCode(), null, null, null, null);
            }
            //减用户的现金
            userDao.subCash(requestUserId, amount);
            return R.ok(vo);
        } else {
            if (withdrawalType.equals(WithdrawalTabEnum.WEI.getCode())) {
                //微信
                //查询用户绑定的的openID，调用微信提现功能
                TransferBillsResult transferBillsResult = wxService.transferBatches("提现", "奖励",
                        amount.multiply(new BigDecimal(100)).intValue(), orderNo, "活动奖励", withdrawalUser.getOpenId());
                //添加记录
                addBillCash(withdrawalType, BillCashTypeEnum.TYPE_2.getCode(), orderNo, requestUserId, BillSceneTypeEnum.SCENE_2.getCode(), "申请提现",
                        amount, date, withdrawalTabId, autoAccount, BillCashTakeEnum.TYPE_2.getCode(),
                        BillCashStatusEnum.STATUS_3.getCode(), null, transferBillsResult.getPackageInfo(),null,null);
            } else if (withdrawalType.equals(WithdrawalTabEnum.ALI.getCode())) {
                //支付宝
                aliService.entPay(withdrawalUser.getUserId(), withdrawalUser.getOpenId(), amount);
                //添加记录
                addBillCash(withdrawalType, BillCashTypeEnum.TYPE_2.getCode(), orderNo, requestUserId, BillSceneTypeEnum.SCENE_2.getCode(), "申请提现",
                        amount, date, withdrawalTabId, autoAccount, BillCashTakeEnum.TYPE_2.getCode(),
                        BillCashStatusEnum.STATUS_1.getCode(), date, null, user.getZfbName(), user.getZfbUserName());
            }
            //减用户的现金
            userDao.subCash(requestUserId, amount);
            return R.ok(vo);
        }
    }


    @Override
    public List<BillCashListVO> getMyBillCashListVO() {
        List<BillCashListVO> billCashListVOS = billCashDao.selectMyBillCashListVO(JwtUtil.getRequestUserId(request));
        if (!billCashListVOS.isEmpty()) {
            List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_3.getCode());
            String appId = paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_10.getCode()).getValue();
            String mchId = paramsConfigService.getFollowParams(paramsList, ParamsConfigFollowTypeEnum.TYPE_12.getCode()).getValue();
            for (BillCashListVO billCashListVO : billCashListVOS) {
                //查看是不是微信提现
                if (Objects.nonNull(billCashListVO.getMethod()) && billCashListVO.getMethod() == 1) {
                    //查看当前状态
                    if (!billCashListVO.getStatus().equals(BillCashStatusEnum.STATUS_3.getCode())) {
                        billCashListVO.setAppId(null);
                        billCashListVO.setMchId(null);
                        billCashListVO.setPackageInfo(null);
                    } else {
                        billCashListVO.setAppId(appId);
                        billCashListVO.setMchId(mchId);
                    }
                }
            }
        }
        return billCashListVOS;
    }
}
