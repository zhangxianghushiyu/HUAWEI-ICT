package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 短剧分类
 */
@Data
public class DramaCategory implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 类型名称
     */
    private String name;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 状态（0：删除，1：正常）
     */
    private Integer delStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 子分类
     */
    private List<DramaCategory> children;

    private static final long serialVersionUID = 1L;
}