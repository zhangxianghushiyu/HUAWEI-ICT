package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动对象
 */
@Data
public class ActivityRegistration implements Serializable {
    
    /** 主键 */
    private Long id;

    /** 活动标题 */
    private String title;

    /** 活动描述 */
    private String description;

    /** 封面图 */
    private String coverImage;

    /** 开始时间 */
    private Date startTime;

    /** 结束时间 */
    private Date endTime;

    /** 状态（0：未开始，1：进行中，2：已结束） */
    private Integer status;

    /** 活动地点 */
    private String location;

    /** 主办方 */
    private String organizer;

    /** 联系电话 */
    private String contactPhone;

    /** 删除状态（0：删除，1：正常） */
    private Integer delStatus;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
