package com.ruoyi.platform.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文章对象 t_article
 * 
 * @author yq
 * @date 2026-04-16
 */
@Data
public class Article extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 名 */
    @Excel(name = "名")
    private String articleName;

    /** 标题 */
    @Excel(name = "标题")
    private String articleTitle;

    /** 封面 */
    @Excel(name = "封面")
    private String articleImg;

    /** 内容 */
    private String articleContent;

    /** 浏览量 */
    @Excel(name = "浏览量")
    private Integer browseSum;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Integer likeSum;

    /** 评论数 */
    @Excel(name = "评论数")
    private Integer commentSum;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("articleName", getArticleName())
            .append("articleTitle", getArticleTitle())
            .append("articleImg", getArticleImg())
            .append("articleContent", getArticleContent())
            .append("browseSum", getBrowseSum())
            .append("likeSum", getLikeSum())
            .append("commentSum", getCommentSum())
            .append("createTime", getCreateTime())
            .toString();
    }
}
