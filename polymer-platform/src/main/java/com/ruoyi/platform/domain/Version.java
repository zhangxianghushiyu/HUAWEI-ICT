package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 版本信息
 * t_version
 */
@Data
public class Version implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 版本号
     */
    private String versionNo;

    /**
     * 版本说明
     */
    private String releaseNote;

    /**
     * 下载地址
     */
    private String appUrl;

    /**
     * 图片地址
     */
    private String url;

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