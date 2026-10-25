package com.ruoyi.platform.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class DramaWatchListVO implements Serializable {


    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 短剧ID
     */
    private Long dramaId;

    /**
     * 短剧详细ID
     */
    private Long dramaDetailsId;

    private String nickName;
    private String avatar;
    private String dramaTitle;
    private String dramaTitleImg;
    private String dramaDetailsName;
    private String dramaDetailsTitleImg;

    private String gatherStr;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Date updateTime;

    public String getGatherStr() {
        return gatherStr;
    }

    public void setGatherStr(String gatherStr) {
        this.gatherStr = gatherStr;
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

    public String getDramaTitle() {
        return dramaTitle;
    }

    public void setDramaTitle(String dramaTitle) {
        this.dramaTitle = dramaTitle;
    }

    public String getDramaTitleImg() {
        return dramaTitleImg;
    }

    public void setDramaTitleImg(String dramaTitleImg) {
        this.dramaTitleImg = dramaTitleImg;
    }

    public String getDramaDetailsName() {
        return dramaDetailsName;
    }

    public void setDramaDetailsName(String dramaDetailsName) {
        this.dramaDetailsName = dramaDetailsName;
    }

    public String getDramaDetailsTitleImg() {
        return dramaDetailsTitleImg;
    }

    public void setDramaDetailsTitleImg(String dramaDetailsTitleImg) {
        this.dramaDetailsTitleImg = dramaDetailsTitleImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDramaId() {
        return dramaId;
    }

    public void setDramaId(Long dramaId) {
        this.dramaId = dramaId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setDramaDetailsId(Long dramaDetailsId) {
        this.dramaDetailsId = dramaDetailsId;
    }

    public Long getDramaDetailsId() {
        return dramaDetailsId;
    }


}