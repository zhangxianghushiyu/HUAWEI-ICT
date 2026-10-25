package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 分类及电子书关联
 * t_ebook_category_info
 */
@Data
public class EbookCategoryInfo implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 电子书ID
     */
    private Integer ebookId;

    /**
     * 分类ID
     */
    private Integer categoryId;

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