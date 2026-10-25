package com.ruoyi.platform.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 短剧分类对象 t_drama_category
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
@Data
public class DramaCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 父级id */
    @Excel(name = "父级id")
    private Integer parentId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 删除状态 */
    @Excel(name = "删除状态")
    private Integer delStatus;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sort", getSort())
            .append("name", getName())
            .append("delStatus", getDelStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
