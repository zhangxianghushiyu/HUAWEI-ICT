package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 官方二维码信息
 * t_official_code
 */
@Data
public class OfficialCode implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 内容
     */
    private String content;

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