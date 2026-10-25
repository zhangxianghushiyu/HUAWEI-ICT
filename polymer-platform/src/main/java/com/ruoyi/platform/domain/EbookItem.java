package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 电子书章节
 * t_ebook_item
 */
@Data
public class EbookItem implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 主信息ID
     */
    private Integer ebookId;

    /**
     * 章节号
     */
    private Integer chapter;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 0：删除，1：正常
     */
    private Integer delStatus;

    /**
     * 0：下架，1：上架
     */
    private Integer upStatus;

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