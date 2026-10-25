package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 风控管理
 * t_detection
 */
@Data
public class Detection implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 1：root检测，2：模拟器检测，3：两次广告间隔时间检测，4：单次收益大于某个金额检测，5：连续N条收益低于某个金额检测，6：同一个IP地址N个账号使用检测，7：每个用户每天激励广告限制数量
     */
    private Integer type;

    /**
     * 0：关闭，1：开启
     */
    private Integer status;

    /**
     * 值1
     */
    private String value1;

    /**
     * 值2
     */
    private String value2;

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