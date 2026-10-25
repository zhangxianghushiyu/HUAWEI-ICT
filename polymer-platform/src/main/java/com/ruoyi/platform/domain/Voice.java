package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 语音信息
 * t_voice
 */
@Data
public class Voice implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 1：每次看视频就能获得红包奖励哦，过关越多，红包价值越多，2：点击全部领取，即可获得翻倍红包哦，3：猜对歌曲赚零钱，自动打款到微信，4：回答正确，5：回答错误
     */
    private Integer type;

    /**
     * 地址
     */
    private String url;

    /**
     * 备注
     */
    private String remarks;

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