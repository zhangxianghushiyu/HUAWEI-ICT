package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 电子书主信息
 * t_ebook
 */
@Data
public class Ebook implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 字数
     */
    private Long words;

    /**
     * 封面图
     */
    private String url;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 分数
     */
    private Double fraction;

    /**
     * 标题
     */
    private String title;

    /**
     * 1：连载，2：完结
     */
    private Integer isEnd;

    /**
     * 1：精品，2：普通
     */
    private Integer delicate;

    /**
     * 作者
     */
    private String author;

    /**
     * 介绍
     */
    private String introduce;

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