package com.ruoyi.platform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class VideoCollectListDTO {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 视频ID
     */
    private Long videoId;


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

    private String name;

    private String nickName;

}
