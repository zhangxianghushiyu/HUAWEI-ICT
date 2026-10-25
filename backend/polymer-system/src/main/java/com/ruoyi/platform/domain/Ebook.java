package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 电子书主信息对象 t_ebook
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
public class Ebook extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 字数 */
    @Excel(name = "字数")
    private Long words;

    /** 封面图 */
    @Excel(name = "封面图")
    private String url;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 分数 */
    @Excel(name = "分数")
    private BigDecimal fraction;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 是否完结 */
    @Excel(name = "是否完结")
    private Integer isEnd;

    /** 是否精品 */
    @Excel(name = "是否精品")
    private Integer delicate;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 介绍 */
    @Excel(name = "介绍")
    private String introduce;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer delStatus;

    /** 是否上架 */
    @Excel(name = "是否上架")
    private Integer upStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWords(Long words) 
    {
        this.words = words;
    }

    public Long getWords() 
    {
        return words;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setFraction(BigDecimal fraction) 
    {
        this.fraction = fraction;
    }

    public BigDecimal getFraction() 
    {
        return fraction;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setIsEnd(Integer isEnd) 
    {
        this.isEnd = isEnd;
    }

    public Integer getIsEnd() 
    {
        return isEnd;
    }
    public void setDelicate(Integer delicate) 
    {
        this.delicate = delicate;
    }

    public Integer getDelicate() 
    {
        return delicate;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setIntroduce(String introduce) 
    {
        this.introduce = introduce;
    }

    public String getIntroduce() 
    {
        return introduce;
    }
    public void setDelStatus(Integer delStatus) 
    {
        this.delStatus = delStatus;
    }

    public Integer getDelStatus() 
    {
        return delStatus;
    }
    public void setUpStatus(Integer upStatus) 
    {
        this.upStatus = upStatus;
    }

    public Integer getUpStatus() 
    {
        return upStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("words", getWords())
            .append("url", getUrl())
            .append("sort", getSort())
            .append("fraction", getFraction())
            .append("title", getTitle())
            .append("isEnd", getIsEnd())
            .append("delicate", getDelicate())
            .append("author", getAuthor())
            .append("introduce", getIntroduce())
            .append("delStatus", getDelStatus())
            .append("upStatus", getUpStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
