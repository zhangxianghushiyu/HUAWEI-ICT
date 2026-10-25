package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * taKu广告回调对象 t_ta_ku_callback
 * 
 * @author ruoyi
 * @date 2025-02-27
 */
public class TaKuCallback extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 场景 */
    @Excel(name = "场景")
    private Integer scene;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal money;

    /** 用户id(调用SDK透传，应用对用户的唯一标识) */
    @Excel(name = "用户id(调用SDK透传，应用对用户的唯一标识)")
    private Long userId;

    /** 用户业务参数 */
    @Excel(name = "用户业务参数")
    private String extraData;

    /** 交易id(完成观看的唯一交易ID) */
    @Excel(name = "交易id(完成观看的唯一交易ID)")
    private String transId;

    /** 请求ID */
    @Excel(name = "请求ID")
    private String reqId;

    /** 激励名称 */
    @Excel(name = "激励名称")
    private String rewardName;

    /** 激励数量 */
    @Excel(name = "激励数量")
    private String rewardAmount;

    /** Taku广告位ID */
    @Excel(name = "Taku广告位ID")
    private String placementId;

    /** Taku聚合的广告平台ID */
    @Excel(name = "Taku聚合的广告平台ID")
    private String networkFirmId;

    /** Taku广告场景ID */
    @Excel(name = "Taku广告场景ID")
    private String scenarioId;

    /** 开发者应用的包名或BundleID */
    @Excel(name = "开发者应用的包名或BundleID")
    private String packageName;

    /** 系统平台，Android 或 iOS */
    @Excel(name = "系统平台，Android 或 iOS")
    private String platform;

    /** Taku SDK回调的展示级别数据. 数据为json字符串 */
    @Excel(name = "Taku SDK回调的展示级别数据. 数据为json字符串")
    private String ilrd;

    /** 签名信息 */
    @Excel(name = "签名信息")
    private String sign;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    private String userName;
    private String nickName;
    private String avatar;
    private String networkFirmIdStr;

    private Date[] time;

    private Date startTime;

    private Date endTime;

    //获取 ECPM，单位可通过"currency"获取
    private String adsourcePrice;

    //获取 展示收益
    private String publisherRevenue;

    public Date[] getTime() {
        return time;
    }

    public void setTime(Date[] time) {
        this.time = time;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getNetworkFirmIdStr() {
        return networkFirmIdStr;
    }

    public void setNetworkFirmIdStr(String networkFirmIdStr) {
        this.networkFirmIdStr = networkFirmIdStr;
    }

    public String getAdsourcePrice() {
        return adsourcePrice;
    }

    public void setAdsourcePrice(String adsourcePrice) {
        this.adsourcePrice = adsourcePrice;
    }

    public String getPublisherRevenue() {
        return publisherRevenue;
    }

    public void setPublisherRevenue(String publisherRevenue) {
        this.publisherRevenue = publisherRevenue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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
    public void setExtraData(String extraData) 
    {
        this.extraData = extraData;
    }

    public String getExtraData() 
    {
        return extraData;
    }
    public void setTransId(String transId) 
    {
        this.transId = transId;
    }

    public String getTransId() 
    {
        return transId;
    }
    public void setReqId(String reqId) 
    {
        this.reqId = reqId;
    }

    public String getReqId() 
    {
        return reqId;
    }
    public void setRewardName(String rewardName) 
    {
        this.rewardName = rewardName;
    }

    public String getRewardName() 
    {
        return rewardName;
    }
    public void setRewardAmount(String rewardAmount) 
    {
        this.rewardAmount = rewardAmount;
    }

    public String getRewardAmount() 
    {
        return rewardAmount;
    }
    public void setPlacementId(String placementId) 
    {
        this.placementId = placementId;
    }

    public String getPlacementId() 
    {
        return placementId;
    }
    public void setNetworkFirmId(String networkFirmId) 
    {
        this.networkFirmId = networkFirmId;
    }

    public String getNetworkFirmId() 
    {
        return networkFirmId;
    }
    public void setScenarioId(String scenarioId) 
    {
        this.scenarioId = scenarioId;
    }

    public String getScenarioId() 
    {
        return scenarioId;
    }
    public void setPackageName(String packageName) 
    {
        this.packageName = packageName;
    }

    public String getPackageName() 
    {
        return packageName;
    }
    public void setPlatform(String platform) 
    {
        this.platform = platform;
    }

    public String getPlatform() 
    {
        return platform;
    }
    public void setIlrd(String ilrd) 
    {
        this.ilrd = ilrd;
    }

    public String getIlrd() 
    {
        return ilrd;
    }
    public void setSign(String sign) 
    {
        this.sign = sign;
    }

    public String getSign() 
    {
        return sign;
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
            .append("extraData", getExtraData())
            .append("transId", getTransId())
            .append("reqId", getReqId())
            .append("rewardName", getRewardName())
            .append("rewardAmount", getRewardAmount())
            .append("placementId", getPlacementId())
            .append("networkFirmId", getNetworkFirmId())
            .append("scenarioId", getScenarioId())
            .append("packageName", getPackageName())
            .append("platform", getPlatform())
            .append("ilrd", getIlrd())
            .append("sign", getSign())
            .append("remarks", getRemarks())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
