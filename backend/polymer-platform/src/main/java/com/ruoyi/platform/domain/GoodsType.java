package com.ruoyi.platform.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 商品分类对象 t_goods_type
 * 
 * @author yq
 * @date 2025-09-01
 */
@Data
public class GoodsType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String typeName;

    /** 排序 */
    @Excel(name = "排序")
    private Integer typeSort;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("typeName", getTypeName())
            .append("typeSort", getTypeSort())
            .append("createTime", getCreateTime())
            .toString();
    }
}
