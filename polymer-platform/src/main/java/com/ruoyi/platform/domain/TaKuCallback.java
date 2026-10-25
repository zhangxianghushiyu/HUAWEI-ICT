package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * taKu广告回调
 * t_ta_ku_callback
 */
@Data
public class TaKuCallback implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 1：宝箱，2：看小视频，3：每日签到，4：看短剧，5：宝箱二次广告，6：看广告解锁短剧
     */
    private Integer scene;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 用户id(调用SDK透传，应用对用户的唯一标识)
     */
    private Integer userId;

    /**
     * 短剧解锁标识（1：已解锁，2：未解锁）
     */
    private Integer unlock;

    /**
     * 用户业务参数
     */
    private String extraData;

    /**
     * 预算金额（元）
     */
    private String publisherRevenue;

    /**
     * ECPM值
     */
    private String adsourcePrice;

    /**
     * 交易id(完成观看的唯一交易ID)
     */
    private String transId;

    /**
     * 请求ID
     */
    private String reqId;

    /**
     * 激励名称
     */
    private String rewardName;

    /**
     * 激励数量
     */
    private String rewardAmount;

    /**
     * Taku广告位ID
     */
    private String placementId;

    /**
     * Taku聚合的广告平台ID
     */
    private String networkFirmId;

    /**
     * Taku广告场景ID
     */
    private String scenarioId;

    /**
     * 开发者应用的包名或BundleID
     */
    private String packageName;

    /**
     * 系统平台，Android 或 iOS
     */
    private String platform;

    /**
     * Taku SDK回调的展示级别数据. 数据为json字符串
     */
    private String ilrd;

    /**
     * 签名信息
     */
    private String sign;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}