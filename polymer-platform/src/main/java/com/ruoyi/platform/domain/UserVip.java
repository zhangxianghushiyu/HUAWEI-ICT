package com.ruoyi.platform.domain;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户VIP信息
 * t_user_vip
 */
@Data
public class UserVip implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

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
