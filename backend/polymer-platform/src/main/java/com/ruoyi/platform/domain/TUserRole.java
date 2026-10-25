package com.ruoyi.platform.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 用户身份对象 t_user_role
 * 
 * @author ruoyi
 * @date 2025-07-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TUserRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户身份 1代理 2团长 3团员 0普通用户 */
    @Excel(name = "用户身份 1代理 2团长 3团员 0普通用户")
    private Long roleType;

    /** 抽佣比例 */
    @Excel(name = "抽佣比例")
    private BigDecimal ratio;

    /** 看广告比例 */
    @Excel(name = "看广告比例")
    private BigDecimal myRatio;

    /** 间推抽佣比例 */
    @Excel(name = "间推抽佣比例")
    private BigDecimal indirectRatio;
}
