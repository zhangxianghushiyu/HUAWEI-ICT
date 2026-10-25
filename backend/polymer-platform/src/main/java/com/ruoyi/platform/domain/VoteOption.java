package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 投票选项
 */
@Data
public class VoteOption implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 投票活动ID
     */
    private Integer activityId;

    /**
     * 选项内容
     */
    private String optionContent;

    /**
     * 选项图片
     */
    private String optionImage;

    /**
     * 票数
     */
    private Integer voteCount;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 删除状态（0：删除，1：正常）
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

    private static final long serialVersionUID = 1L;
}
