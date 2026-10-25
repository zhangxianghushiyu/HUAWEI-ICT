package com.ruoyi.platform.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 版本信息对象 t_version
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
public class Version extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 版本号 */
    @Excel(name = "版本号")
    private String versionNo;

    /** 版本说明 */
    @Excel(name = "版本说明")
    private String releaseNote;

    /** 下载地址 */
    @Excel(name = "下载地址")
    private String appUrl;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setVersionNo(String versionNo) 
    {
        this.versionNo = versionNo;
    }

    public String getVersionNo() 
    {
        return versionNo;
    }
    public void setReleaseNote(String releaseNote) 
    {
        this.releaseNote = releaseNote;
    }

    public String getReleaseNote() 
    {
        return releaseNote;
    }
    public void setAppUrl(String appUrl) 
    {
        this.appUrl = appUrl;
    }

    public String getAppUrl() 
    {
        return appUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("versionNo", getVersionNo())
            .append("releaseNote", getReleaseNote())
            .append("appUrl", getAppUrl())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
