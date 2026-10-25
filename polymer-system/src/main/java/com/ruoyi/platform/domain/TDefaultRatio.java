package com.ruoyi.platform.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 统一默认比例对象 t_default_ratio
 * 
 * @author ruoyi
 * @date 2025-07-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TDefaultRatio extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 有上级比例 */
    @Excel(name = "有上级比例")
    private BigDecimal haveManagerRatio;

    /** 无上级比例 */
    @Excel(name = "无上级比例")
    private BigDecimal noManagerRatio;

    /** 玩家比例 */
    @Excel(name = "玩家比例")
    private BigDecimal userRatio;
}
