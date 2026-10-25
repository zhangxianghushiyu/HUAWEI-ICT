package com.ruoyi.platform.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户阶级记录对象 t_user_grade
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public class UserGrade extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Integer userId;

    /** 用户等级 */
    @Excel(name = "用户等级")
    private Integer grade;

    /** 用户当前等级答对的关卡数 */
    @Excel(name = "用户当前等级答对的关卡数")
    private Integer barrier;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
    }
    public void setGrade(Integer grade) 
    {
        this.grade = grade;
    }

    public Integer getGrade() 
    {
        return grade;
    }
    public void setBarrier(Integer barrier) 
    {
        this.barrier = barrier;
    }

    public Integer getBarrier() 
    {
        return barrier;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("grade", getGrade())
            .append("barrier", getBarrier())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
