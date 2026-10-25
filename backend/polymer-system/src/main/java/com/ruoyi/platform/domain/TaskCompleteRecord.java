package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务完成记录对象 t_task_complete_record
 * 
 * @author ruoyi
 * @date 2024-11-19
 */
public class TaskCompleteRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long taskRewardId;

    /** 任务标题 */
    @Excel(name = "任务标题")
    private String taskTitle;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String taskDescribe;

    /** 类型 */
    @Excel(name = "类型")
    private Integer taskType;

    /** 副类型 */
    @Excel(name = "副类型")
    private Integer taskFollowType;

    /** 奖励类型（1：金币，2：现金，3：人民币） */
    @Excel(name = "奖励类型", readConverterExp = "1=：金币，2：现金，3：人民币")
    private Integer taskRewardType;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal taskPrice;

    /** 描述 */
    @Excel(name = "描述")
    private String describe;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTaskRewardId(Long taskRewardId) 
    {
        this.taskRewardId = taskRewardId;
    }

    public Long getTaskRewardId() 
    {
        return taskRewardId;
    }
    public void setTaskTitle(String taskTitle) 
    {
        this.taskTitle = taskTitle;
    }

    public String getTaskTitle() 
    {
        return taskTitle;
    }
    public void setTaskDescribe(String taskDescribe) 
    {
        this.taskDescribe = taskDescribe;
    }

    public String getTaskDescribe() 
    {
        return taskDescribe;
    }
    public void setTaskType(Integer taskType) 
    {
        this.taskType = taskType;
    }

    public Integer getTaskType() 
    {
        return taskType;
    }
    public void setTaskFollowType(Integer taskFollowType) 
    {
        this.taskFollowType = taskFollowType;
    }

    public Integer getTaskFollowType() 
    {
        return taskFollowType;
    }
    public void setTaskRewardType(Integer taskRewardType) 
    {
        this.taskRewardType = taskRewardType;
    }

    public Integer getTaskRewardType() 
    {
        return taskRewardType;
    }
    public void setTaskPrice(BigDecimal taskPrice) 
    {
        this.taskPrice = taskPrice;
    }

    public BigDecimal getTaskPrice() 
    {
        return taskPrice;
    }
    public void setDescribe(String describe) 
    {
        this.describe = describe;
    }

    public String getDescribe() 
    {
        return describe;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("taskRewardId", getTaskRewardId())
            .append("taskTitle", getTaskTitle())
            .append("taskDescribe", getTaskDescribe())
            .append("taskType", getTaskType())
            .append("taskFollowType", getTaskFollowType())
            .append("taskRewardType", getTaskRewardType())
            .append("taskPrice", getTaskPrice())
            .append("describe", getDescribe())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
