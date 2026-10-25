package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 电子书分类
 * t_ebook_category
 */
@Data
public class EbookCategory implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 分类名称
     */
    private String name;

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