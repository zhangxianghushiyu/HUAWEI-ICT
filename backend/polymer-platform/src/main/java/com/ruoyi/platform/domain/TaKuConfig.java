package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * taKu广告配置
 * t_ta_ku_config
 */
@Data
public class TaKuConfig implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 类型（1：开屏广告，2：激励视频，3：appId，4：appKey）
     */
    private Integer type;

    /**
     * 值
     */
    private String value;

    /**
     * 签名
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