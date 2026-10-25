package com.ruoyi.platform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 短剧解锁配置对象 t_drama_unlock
 * 
 * @author ruoyi
 * @date 2025-01-17
 */
public class DramaUnlock extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 免费集数 */
    @Excel(name = "免费集数")
    private Integer freeNum;

    /** 解锁集数 */
    @Excel(name = "解锁集数")
    private Integer unlockNum;

    private BigDecimal money;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFreeNum(Integer freeNum) 
    {
        this.freeNum = freeNum;
    }

    public Integer getFreeNum() 
    {
        return freeNum;
    }
    public void setUnlockNum(Integer unlockNum) 
    {
        this.unlockNum = unlockNum;
    }

    public Integer getUnlockNum() 
    {
        return unlockNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("freeNum", getFreeNum())
            .append("unlockNum", getUnlockNum())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
