package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 曲库信息
 * t_music_store
 */
@Data
public class MusicStore implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 歌名
     */
    private String name;

    /**
     * 答案
     */
    private String answer;

    /**
     * 错误
     */
    private String wrong;

    /**
     * 地址
     */
    private String url;

    /**
     * 0：禁用，1：正常
     */
    private Integer status;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更细时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}