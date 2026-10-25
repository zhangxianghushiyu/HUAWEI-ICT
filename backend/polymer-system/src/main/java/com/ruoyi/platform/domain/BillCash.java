package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 现金明细对象 t_bill_cash
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
public class BillCash extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 提现方式 */
    @Excel(name = "提现方式")
    private Integer method;

    /** 类型（1：任务，2：提现） */
    @Excel(name = "类型（1：任务，2：提现）")
    private Integer type;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 失败原因 */
    @Excel(name = "失败原因")
    private String failureReason;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 场景 */
    @Excel(name = "场景")
    private Integer scene;

    /** 描述 */
    @Excel(name = "描述")
    private String describe;

    /**
     * 微信提现返回信息
     */
	@Excel(name = "微信提现返回信息")
    private String packageInfo;
    /** 选项卡金额 */
    @Excel(name = "选项卡金额")
    private BigDecimal money;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 提现选项卡ID */
    @Excel(name = "提现选项卡ID")
    private Long withdrawalTabId;

    /** 自动打款 */
    @Excel(name = "自动打款")
    private Integer autoAccount;


    /** 场景（1：系统，2：提现） */
    @Excel(name = "场景（1：系统，2：提现）")
    private Integer take;

    /** 打款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "打款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    private String zfbName;
    private String zfbUserName;

    public String getZfbName() {
        return zfbName;
    }

    public void setZfbName(String zfbName) {
        this.zfbName = zfbName;
    }

    public String getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo;
    }

    public String getZfbUserName() {
        return zfbUserName;
    }

    public void setZfbUserName(String zfbUserName) {
        this.zfbUserName = zfbUserName;
    }

    public Integer getTake() {
        return take;
    }

    public void setTake(Integer take) {
        this.take = take;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMethod(Integer method) 
    {
        this.method = method;
    }

    public Integer getMethod() 
    {
        return method;
    }
    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
    }
    public void setFailureReason(String failureReason) 
    {
        this.failureReason = failureReason;
    }

    public String getFailureReason() 
    {
        return failureReason;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setScene(Integer scene) 
    {
        this.scene = scene;
    }

    public Integer getScene() 
    {
        return scene;
    }
    public void setDescribe(String describe) 
    {
        this.describe = describe;
    }

    public String getDescribe() 
    {
        return describe;
    }
    public void setMoney(BigDecimal money) 
    {
        this.money = money;
    }

    public BigDecimal getMoney() 
    {
        return money;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setWithdrawalTabId(Long withdrawalTabId) 
    {
        this.withdrawalTabId = withdrawalTabId;
    }

    public Long getWithdrawalTabId() 
    {
        return withdrawalTabId;
    }
    public void setAutoAccount(Integer autoAccount) 
    {
        this.autoAccount = autoAccount;
    }

    public Integer getAutoAccount() 
    {
        return autoAccount;
    }
    public void setPayTime(Date payTime) 
    {
        this.payTime = payTime;
    }

    public Date getPayTime() 
    {
        return payTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("method", getMethod())
            .append("orderNo", getOrderNo())
            .append("failureReason", getFailureReason())
            .append("userId", getUserId())
            .append("scene", getScene())
            .append("describe", getDescribe())
            .append("money", getMoney())
            .append("status", getStatus())
            .append("withdrawalTabId", getWithdrawalTabId())
            .append("autoAccount", getAutoAccount())
            .append("payTime", getPayTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
