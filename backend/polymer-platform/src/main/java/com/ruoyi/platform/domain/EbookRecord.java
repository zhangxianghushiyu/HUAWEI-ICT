package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 电子书记录
 * t_ebook_record
 */
@Data
public class EbookRecord implements Serializable {

    /*
    * 记录ID
    * */
    private int id;
    /*
     * 用户id
     * */
    private int userId;

    /*
     * 小说Id
     * */
    private int eBookId;

    /*
     * 章节id
     * */
    private int chapterId;

    /*
    * 阅读进度
    * */
    private int readProgress;

    /*
    *阅读时间
    *
    * */
    private Date readTime;
    /*
     *是否是最新阅读记录（0：否，1：是）
     *
     * */
    private int isLatest;

    /*
     *创建时间
     * */
    private Date createTime;
    /*
     *更新时间
     * */
    private Date updateTime;
}
