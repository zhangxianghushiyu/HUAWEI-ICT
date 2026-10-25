package com.ruoyi.platform.domain;

import java.math.BigDecimal;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 看视频领金币配置对象 t_task_daily_look_video_to_coin
 * 
 * @author ruoyi
 * @date 2024-11-22
 */
@Data
public class TaskDailyLookVideoToCoin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 次数 */
    @Excel(name = "次数")
    private Integer count;

    /** 秒 */
    @Excel(name = "秒")
    private Long secondInfo;

    /** 金币 */
    @Excel(name = "金币")
    private BigDecimal price;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("count", getCount())
            .append("price", getPrice())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
