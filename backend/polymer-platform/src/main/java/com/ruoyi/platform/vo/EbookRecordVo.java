package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EbookRecordVo {

    /*
    * 记录ID
    * */
    @ApiModelProperty(value = "id")
    private int id;
    /*
     * 用户id
     * */
    @ApiModelProperty(value = "用户id")
    private int userId;

    /*
     * 小说Id
     * */
    @ApiModelProperty(value = "小说Id")
    private int ebookId;

    /*
     * 章节id
     * */
    @ApiModelProperty(value = "章节id")
    private int chapterId;

    /*
    * 阅读进度
    * */
    @ApiModelProperty(value = "阅读进度")
    private int readProgress;

    /*
    *阅读时间
    *
    * */
    @ApiModelProperty(value = "阅读时间")
    private String readTime;
    /*
     *是否是最新阅读记录（0：否，1：是）
     *
     * */
    @ApiModelProperty(value = "是否是最新阅读记录（0：否，1：是）")
    private int isLatest;

    /*
     *创建时间
     * */
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    /*
     *更新时间
     * */
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    /*
     *小说title
     * */
    @ApiModelProperty(value = "小说标题")
    private String title;
    /*
     *章节title
     * */
    @ApiModelProperty(value = "章节标题")
    private String chapterTitle;

    /*
     *章节title
     * */
    @ApiModelProperty(value = "小说封面")
    private String url;
}
