package com.ruoyi.platform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 电子书章节对象 t_ebook_item
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
public class EbookItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 主信息ID */
//    @Excel(name = "主信息ID")
    private Long ebookId;

    /** 章节号 */
    @Excel(name = "章节号")
    private Long chapter;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 是否删除 */
//    @Excel(name = "是否删除")
    private Integer delStatus;

    /** 是否上架 */
//    @Excel(name = "是否上架")
    private Integer upStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEbookId(Long ebookId) 
    {
        this.ebookId = ebookId;
    }

    public Long getEbookId() 
    {
        return ebookId;
    }
    public void setChapter(Long chapter) 
    {
        this.chapter = chapter;
    }

    public Long getChapter() 
    {
        return chapter;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
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
            .append("ebookId", getEbookId())
            .append("chapter", getChapter())
            .append("title", getTitle())
            .append("content", getContent())
            .append("delStatus", getDelStatus())
            .append("upStatus", getUpStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
