package com.ruoyi.platform.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文章评论对象 t_article_comment
 * 
 * @author yq
 * @date 2026-04-16
 */
@Data
public class ArticleComment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Integer userId;
    private String userName;
    private String userAvatar;

    /** 文章id */
    @Excel(name = "文章id")
    private Integer articleId;

    /** 类型 1文字 2图片 */
    @Excel(name = "类型 1文字 2图片")
    private Integer type;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String content;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("articleId", getArticleId())
            .append("type", getType())
            .append("content", getContent())
            .append("createTime", getCreateTime())
            .toString();
    }
}
