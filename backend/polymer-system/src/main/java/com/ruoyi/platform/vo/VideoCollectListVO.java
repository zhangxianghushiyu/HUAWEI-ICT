package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class VideoCollectListVO implements Serializable {
    private Long id;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 视频标题
     */
    private String videoName;
    /**
     * 视频封面
     */
    private String videoCover;
    /**
     * 视频路径
     */
    private String videoUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Date updateTime;

}
