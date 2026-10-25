package com.ruoyi.platform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 风控管理对象 t_detection
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
public class Detection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 1：root检测，2：模拟器检测，3：两次广告间隔时间检测，4：单次收益大于某个金额检测，5：连续N条收益低于某个金额检测，6：同一个IP地址N个账号使用检测，7：每个用户每天激励广告限制数量 */
    @Excel(name = "1：root检测，2：模拟器检测，3：两次广告间隔时间检测，4：单次收益大于某个金额检测，5：连续N条收益低于某个金额检测，6：同一个IP地址N个账号使用检测，7：每个用户每天激励广告限制数量")
    private Long type;

    /** 0：关闭，1：开启 */
    @Excel(name = "0：关闭，1：开启")
    private Integer status;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    private String value1;
    private String value2;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("status", getStatus())
            .append("remarks", getRemarks())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
