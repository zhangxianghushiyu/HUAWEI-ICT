package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 短剧收藏
 */
@Data
public class DramaCollect implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 短剧ID
     */
    private Integer dramaId;

    /**
     * 短剧详细ID
     */
    private Integer dramaDetailsId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间（最近一次收藏时间）
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}