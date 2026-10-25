package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 参数配置
 * t_params_config
 */
@Data
public class ParamsConfig implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 类型（1：阿里云短信，2：OSS对象存储）
     */
    private Integer type;

    /**
     * 副类型（1：阿里短信accessKeyId，2：阿里云短信accessKeySecret，3：阿里云短信endpoint，4：阿里云短信signName，5：阿里云短信templateCode，6：OSSbucketName，7：OSSendpoint）
     */
    private Integer followType;

    /**
     * 值
     */
    private String value;

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