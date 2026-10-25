package com.ruoyi.platform.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 申请代理对象 t_apply_agent
 * 
 * @author ruoyi
 * @date 2025-07-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class TApplyAgent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 状态（0：申请中，1：已拒绝，2：已同意） */
    @Excel(name = "状态", readConverterExp = "0=：申请中，1：已拒绝，2：已同意")
    private Long status;

    private User user;
}
