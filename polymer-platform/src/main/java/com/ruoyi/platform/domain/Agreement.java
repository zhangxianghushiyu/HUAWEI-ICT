package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 协议信息
 * t_agreement
 */
@Data
public class Agreement implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 类型（1：用户手册，2：隐私政策，3：收集个人信息明示清单，4：个人信息第三方共享清单，5：儿童个人信息保护规则，
     * 6：青少年文明公约，7：注销协议，8：福利规则，9：会员服务协议，10：推广协议）
     */
    private Integer type;

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