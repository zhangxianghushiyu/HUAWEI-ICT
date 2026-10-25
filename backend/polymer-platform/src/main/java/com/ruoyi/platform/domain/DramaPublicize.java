package com.ruoyi.platform.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 短剧广告观看次数
 * t_drama_publicize
 */
@Data
public class DramaPublicize implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 单集ID
     */
    private Integer dramaDetailsId;
    /**
     * 0：未解锁，1：已解锁
     */
    private Integer unlock;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "DramaPublicize{" +
                "id=" + id +
                ", userId=" + userId +
                ", dramaDetailsId=" + dramaDetailsId +
                ", unlock=" + unlock +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}