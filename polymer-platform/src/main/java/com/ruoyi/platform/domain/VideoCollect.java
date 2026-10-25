package com.ruoyi.platform.domain;

import lombok.Data;

import java.util.Date;

@Data
public class VideoCollect {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 视频id
     */
    private Integer videoId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
