package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 广告回调对象 t_ad_callback
 * 
 * @author ruoyi
 * @date 2025-01-08
 */
public class AdCallback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 场景 */
    @Excel(name = "场景")
    private Integer scene;

    /** 用户金额 */
    @Excel(name = "用户金额")
    private BigDecimal money;

    /** 用户id(调用SDK透传，应用对用户的唯一标识) */
    @Excel(name = "用户id(调用SDK透传，应用对用户的唯一标识)")
    private Long userId;

    /** 获取广告ecpm价格，无数据返回null */
    @Excel(name = "获取广告ecpm价格，无数据返回null")
    private String ecpm;

    /** 签名 */
    @Excel(name = "签名")
    private String sign;

    /** 交易id(完成观看的唯一交易ID) */
    @Excel(name = "交易id(完成观看的唯一交易ID)")
    private String transId;

    /** 奖励数量（媒体平台配置或调用SDK传入） */
    @Excel(name = "奖励数量", readConverterExp = "媒=体平台配置或调用SDK传入")
    private Long rewardAmount;

    /** 奖励名称（媒体平台配置或调用SDK传入） */
    @Excel(name = "奖励名称", readConverterExp = "媒=体平台配置或调用SDK传入")
    private String rewardName;

    /** 代码位id */
    @Excel(name = "代码位id")
    private String mediationRit;

    /** 广告位id */
    @Excel(name = "广告位id")
    private String primeRit;

    /** 广告网络对应的名称，无数据返回null */
    @Excel(name = "广告网络对应的名称，无数据返回null")
    private String adnName;

    /** 自定义数据(调用SDK传入并透传，如无需要则为空) */
    @Excel(name = "自定义数据(调用SDK传入并透传，如无需要则为空)")
    private String extra;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setScene(Integer scene) 
    {
        this.scene = scene;
    }

    public Integer getScene() 
    {
        return scene;
    }
    public void setMoney(BigDecimal money) 
    {
        this.money = money;
    }

    public BigDecimal getMoney() 
    {
        return money;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setEcpm(String ecpm) 
    {
        this.ecpm = ecpm;
    }

    public String getEcpm() 
    {
        return ecpm;
    }
    public void setSign(String sign) 
    {
        this.sign = sign;
    }

    public String getSign() 
    {
        return sign;
    }
    public void setTransId(String transId) 
    {
        this.transId = transId;
    }

    public String getTransId() 
    {
        return transId;
    }
    public void setRewardAmount(Long rewardAmount) 
    {
        this.rewardAmount = rewardAmount;
    }

    public Long getRewardAmount() 
    {
        return rewardAmount;
    }
    public void setRewardName(String rewardName) 
    {
        this.rewardName = rewardName;
    }

    public String getRewardName() 
    {
        return rewardName;
    }
    public void setMediationRit(String mediationRit) 
    {
        this.mediationRit = mediationRit;
    }

    public String getMediationRit() 
    {
        return mediationRit;
    }
    public void setPrimeRit(String primeRit) 
    {
        this.primeRit = primeRit;
    }

    public String getPrimeRit() 
    {
        return primeRit;
    }
    public void setAdnName(String adnName) 
    {
        this.adnName = adnName;
    }

    public String getAdnName() 
    {
        return adnName;
    }
    public void setExtra(String extra) 
    {
        this.extra = extra;
    }

    public String getExtra() 
    {
        return extra;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("scene", getScene())
            .append("money", getMoney())
            .append("userId", getUserId())
            .append("ecpm", getEcpm())
            .append("sign", getSign())
            .append("transId", getTransId())
            .append("rewardAmount", getRewardAmount())
            .append("rewardName", getRewardName())
            .append("mediationRit", getMediationRit())
            .append("primeRit", getPrimeRit())
            .append("adnName", getAdnName())
            .append("extra", getExtra())
            .append("remarks", getRemarks())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
