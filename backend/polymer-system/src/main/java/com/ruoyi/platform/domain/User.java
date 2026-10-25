package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户信息对象 t_user
 *
 * @author ruoyi
 * @date 2024-11-08
 */
@Data
public class User extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 账号 */
    @Excel(name = "账号")
    private String userName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 盐 */
    @Excel(name = "盐")
    private String salt;

    /** 邀请码 */
    @Excel(name = "邀请码")
    private String invitationCode;

    private String qrUrl;
    private String qrCode;

    /** 是否VIP */
    @Excel(name = "是否VIP")
    private Integer isVip;

    /** 父级用户ID */
    @Excel(name = "父级用户ID")
    private Long parentId;

    /** 邀请时间 */
    @Excel(name = "邀请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invitationTime;

    /** 注销时间 */
    @Excel(name = "注销时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date destroyTime;

    /** 删除状态 */
    @Excel(name = "删除状态")
    private Integer delStatus;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 用户角色id */
    @Excel(name = "用户角色id")
    private Integer roleId;

    /** 是否代理 */
    @Excel(name = "是否代理")
    private Integer commander;

    /** 代理比例 */
    @Excel(name = "代理比例")
    private BigDecimal commanderRatio;

    /** 有上级比例 */
    @Excel(name = "有上级比例")
    private BigDecimal haveManagerRatio;

    /** 无上级比例 */
    @Excel(name = "无上级比例")
    private BigDecimal noManagerRatio;


    /** 金币 */
    @Excel(name = "金币")
    private BigDecimal goldCoin;

    /** 现金 */
    @Excel(name = "现金")
    private BigDecimal cash;

    private String zfbName;
    private String zfbUserName;

    private String wxOpenId;
    private String wxAvatar;
    private String wxNickName;
    private String wxUnionId;
    private Integer grade;
    private Integer barrier;


    private Integer getType;
    private BigDecimal goldMin;
    private BigDecimal goldMax;

    private Integer juniorGetType;
    private BigDecimal juniorGoldMin;
    private BigDecimal juniorGoldMax;

    public Integer getGetType() {
        return getType;
    }

    public void setGetType(Integer getType) {
        this.getType = getType;
    }

    public BigDecimal getGoldMin() {
        return goldMin;
    }

    public void setGoldMin(BigDecimal goldMin) {
        this.goldMin = goldMin;
    }

    public BigDecimal getGoldMax() {
        return goldMax;
    }

    public void setGoldMax(BigDecimal goldMax) {
        this.goldMax = goldMax;
    }

    public Integer getJuniorGetType() {
        return juniorGetType;
    }

    public void setJuniorGetType(Integer juniorGetType) {
        this.juniorGetType = juniorGetType;
    }

    public BigDecimal getJuniorGoldMin() {
        return juniorGoldMin;
    }

    public void setJuniorGoldMin(BigDecimal juniorGoldMin) {
        this.juniorGoldMin = juniorGoldMin;
    }

    public BigDecimal getJuniorGoldMax() {
        return juniorGoldMax;
    }

    public void setJuniorGoldMax(BigDecimal juniorGoldMax) {
        this.juniorGoldMax = juniorGoldMax;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getBarrier() {
        return barrier;
    }

    public void setBarrier(Integer barrier) {
        this.barrier = barrier;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getWxAvatar() {
        return wxAvatar;
    }

    public void setWxAvatar(String wxAvatar) {
        this.wxAvatar = wxAvatar;
    }

    public String getWxNickName() {
        return wxNickName;
    }

    public void setWxNickName(String wxNickName) {
        this.wxNickName = wxNickName;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

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

    private String commanderUserName;


    public String getCommanderUserName() {
        return commanderUserName;
    }

    public void setCommanderUserName(String commanderUserName) {
        this.commanderUserName = commanderUserName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getCommander() {
        return commander;
    }

    public void setCommander(Integer commander) {
        this.commander = commander;
    }

    public BigDecimal getCommanderRatio() {
        return commanderRatio;
    }

    public void setCommanderRatio(BigDecimal commanderRatio) {
        this.commanderRatio = commanderRatio;
    }

    public BigDecimal getHaveManagerRatio() {
        return haveManagerRatio;
    }

    public void setHaveManagerRatio(BigDecimal haveManagerRatio) {
        this.haveManagerRatio = haveManagerRatio;
    }

    public BigDecimal getNoManagerRatio() {
        return noManagerRatio;
    }

    public void setNoManagerRatio(BigDecimal noManagerRatio) {
        this.noManagerRatio = noManagerRatio;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getNickName()
    {
        return nickName;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getAvatar()
    {
        return avatar;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }
    public void setInvitationCode(String invitationCode)
    {
        this.invitationCode = invitationCode;
    }

    public String getInvitationCode()
    {
        return invitationCode;
    }
    public void setIsVip(Integer isVip)
    {
        this.isVip = isVip;
    }

    public Integer getIsVip()
    {
        return isVip;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setDelStatus(Integer delStatus)
    {
        this.delStatus = delStatus;
    }

    public Integer getDelStatus()
    {
        return delStatus;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setGoldCoin(BigDecimal goldCoin)
    {
        this.goldCoin = goldCoin;
    }

    public BigDecimal getGoldCoin()
    {
        return goldCoin;
    }

    public Date getInvitationTime() {
        return invitationTime;
    }

    public void setInvitationTime(Date invitationTime) {
        this.invitationTime = invitationTime;
    }

    public Date getDestroyTime() {
        return destroyTime;
    }

    public void setDestroyTime(Date destroyTime) {
        this.destroyTime = destroyTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("nickName", getNickName())
                .append("phone", getPhone())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("invitationCode", getInvitationCode())
                .append("isVip", getIsVip())
                .append("parentId", getParentId())
                .append("delStatus", getDelStatus())
                .append("status", getStatus())
                .append("goldCoin", getGoldCoin())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
