package com.ruoyi.platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class VideoLikeListDTO {
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
     * 用户名称
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
     * 视频播放地址
     */
    private String videoUrl;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;



    private String name;
}
