package com.ruoyi.platform.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BillCashListVO implements Serializable {


    private Long id;

    private Long userId;

    private String nickName;
    private String type;
    private String avatar;

    private Integer method;

    private String orderNo;

    private String failureReason;

    private Integer scene;

    private String describe;

    private BigDecimal money;

    private Integer status;

    private Long withdrawalTabId;

    private Integer autoAccount;

    private String commanderUserName;

    private Integer commander;

    private String zfbName;
    private String zfbUserName;

    public String getZfbName() {
        return zfbName;
    }

    public void setZfbName(String zfbName) {
        this.zfbName = zfbName;
    }

    public String getZfbUserName() {
        return zfbUserName;
    }

    public void setZfbUserName(String zfbUserName) {
        this.zfbUserName = zfbUserName;
    }

    public Integer getCommander() {
        return commander;
    }

    public void setCommander(Integer commander) {
        this.commander = commander;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getCommanderUserName() {
        return commanderUserName;
    }

    public void setCommanderUserName(String commanderUserName) {
        this.commanderUserName = commanderUserName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public Integer getScene() {
        return scene;
    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getWithdrawalTabId() {
        return withdrawalTabId;
    }

    public void setWithdrawalTabId(Long withdrawalTabId) {
        this.withdrawalTabId = withdrawalTabId;
    }

    public Integer getAutoAccount() {
        return autoAccount;
    }

    public void setAutoAccount(Integer autoAccount) {
        this.autoAccount = autoAccount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}