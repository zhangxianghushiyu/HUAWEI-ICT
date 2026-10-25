package com.ruoyi.platform.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 答题记录
 * t_answer_record
 */
@Data
public class AnswerRecord implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 关卡
     */
    private Integer barrier;

    /**
     * 歌名
     */
    private String name;

    /**
     * 答案
     */
    private String answer;

    /**
     * 错误
     */
    private String wrong;

    /**
     * 歌曲地址
     */
    private String url;

    /**
     * 回答
     */
    private String write;

    /**
     * 0：错误，1：正确
     */
    private Integer answerResult;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}