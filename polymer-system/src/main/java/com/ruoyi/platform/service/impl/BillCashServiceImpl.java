package com.ruoyi.platform.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BillCashStatusEnum;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.domain.wx.TransferBillsResult;
import com.ruoyi.platform.dto.BillCashListDTO;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.AliService;
import com.ruoyi.platform.service.IBillCashService;
import com.ruoyi.platform.service.WxService;
import com.ruoyi.platform.vo.BillCashListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 现金明细Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-15
 */
@Service
public class BillCashServiceImpl implements IBillCashService {
    @Autowired
    private BillCashMapper billCashMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WxService wxService;
    @Autowired
    private AliService aliService;
    @Autowired
    private WithdrawalUserMapper withdrawalUserMapper;
    @Autowired
    private ConvertRatioMapper convertRatioMapper;
    @Autowired
    private BillGoldMapper billGoldMapper;

    /**
     * 查询现金明细
     *
     * @param id 现金明细主键
     * @return 现金明细
     */
    @Override
    public BillCash selectBillCashById(Long id) {
        return billCashMapper.selectBillCashById(id);
    }

    /**
     * 查询现金明细列表
     *
     * @param billCash 现金明细
     * @return 现金明细
     */
    @Override
    public List<BillCash> selectBillCashList(BillCash billCash) {
        return billCashMapper.selectBillCashList(billCash);
    }

    /**
     * 新增现金明细
     *
     * @param billCash 现金明细
     * @return 结果
     */
    @Override
    public int insertBillCash(BillCash billCash) {
        billCash.setCreateTime(DateUtils.getNowDate());
        return billCashMapper.insertBillCash(billCash);
    }

    /**
     * 修改现金明细
     *
     * @param billCash 现金明细
     * @return 结果
     */
    @Override
    public int updateBillCash(BillCash billCash) {
        billCash.setUpdateTime(DateUtils.getNowDate());
        return billCashMapper.updateBillCash(billCash);
    }

    /**
     * 批量删除现金明细
     *
     * @param ids 需要删除的现金明细主键
     * @return 结果
     */
    @Override
    public int deleteBillCashByIds(Long[] ids) {
        return billCashMapper.deleteBillCashByIds(ids);
    }

    /**
     * 删除现金明细信息
     *
     * @param id 现金明细主键
     * @return 结果
     */
    @Override
    public int deleteBillCashById(Long id) {
        return billCashMapper.deleteBillCashById(id);
    }

    @Override
    public List<BillCashListVO> selectBillCashListVOInfo(BillCashListDTO dto) {
        return billCashMapper.selectBillCashListVOInfo(dto);
    }


    @Override
    public List<BillCashListVO> getBillGoldListJunior(BillCashListDTO dto) {
        return billCashMapper.selectBillCashListJunior(dto);
    }

    @Override
    @Transactional
    public AjaxResult handleAgreeWith(Long[] ids) throws Exception {
        BillCash billCash = billCashMapper.selectBillCashById(ids[0]);
        WithdrawalUser withdrawalUser = null;
        //查询提现类型
        if (billCash.getMethod() == 1) {
            //微信
            withdrawalUser = withdrawalUserMapper.selectWithdrawalUserMapperByUserIdAndType(billCash.getUserId(), 1);
        } else if (billCash.getMethod() == 2) {
            //支付宝
            withdrawalUser = withdrawalUserMapper.selectWithdrawalUserMapperByUserIdAndType(billCash.getUserId(), 2);
        }
        //查看是不是设置了信息
        if (Objects.isNull(withdrawalUser)) {
            return AjaxResult.error("没有获取到绑定提现信息");
        }
        if (billCash.getMethod() == 1) {
            TransferBillsResult transferBillsResult = wxService.transferBatches("提现", "奖励",
                    billCash.getMoney().multiply(new BigDecimal(100)).intValue(), billCash.getOrderNo(), "活动奖励", withdrawalUser.getOpenId());
            billCash.setStatus(BillCashStatusEnum.STATUS_3.getStatus());
            billCash.setPackageInfo(transferBillsResult.getPackageInfo());
        } else if (billCash.getMethod() == 2) {
            aliService.entPay(withdrawalUser.getOpenId(), billCash.getMoney());
            billCash.setStatus(BillCashStatusEnum.STATUS_1.getStatus());
            billCash.setPayTime(new Date());
        }
        //添加账单标识已经提现
        //修改记录
        billCashMapper.updateBillCash(billCash);
        return AjaxResult.success();
    }

    @Override
    @Transactional
    public int handleRefuse(Long[] ids) {
        BillCash billCash = billCashMapper.selectBillCashById(ids[0]);
        //修改状态为拒绝状态，现金返回给用户
        billCash.setScene(1);
        billCash.setStatus(BillCashStatusEnum.STATUS_2.getStatus());
        billCashMapper.updateBillCash(billCash);
        userMapper.addUserCash(billCash.getUserId(), billCash.getMoney());
        return 1;
    }


    @Override
    @Transactional
    public void goldCoinToCash(User user) {
        ConvertRatio convertRatio = convertRatioMapper.selectConvertRatioInfo();
        int num = user.getGoldCoin().intValue() / convertRatio.getRatio();
        if (num > 0) {
            //加现金
            userMapper.addUserCash(user.getId(), new BigDecimal(num));
            //减金币
            userMapper.subGoldCoin(user.getId(), new BigDecimal(num * convertRatio.getRatio()));
            //添加到记录中
            Date date = new Date();
            BillGold billGold = new BillGold();
            billGold.setUserId(user.getId());
            billGold.setScene(2);
            billGold.setDescribe("金币兑换现金（系统）");
            billGold.setMoney(new BigDecimal(num * convertRatio.getRatio()));
            billGold.setCreateTime(date);
            billGoldMapper.insertBillGold(billGold);

            BillCash billCash = new BillCash();
            billCash.setType(3);
            billCash.setUserId(user.getId());
            billCash.setScene(1);
            billCash.setDescribe("金币兑换现金(系统)");
            billCash.setMoney(new BigDecimal(num));
            billCash.setTake(1);
            billCash.setCreateTime(date);
            billCashMapper.insertBillCash(billCash);
        }
    }

    @Override
    public AjaxResult getStatisticsInfo(BillCashListDTO dto) {
        Float v = billCashMapper.selectBillCashSumMoney(dto);
        return AjaxResult.success(v);
    }

    @Override
    public AjaxResult getStatisticsInfoJunior(BillCashListDTO dto) {
        Float v = billCashMapper.selectBillCashSumMoneyJunior(dto);
        return AjaxResult.success(v);
    }
}
