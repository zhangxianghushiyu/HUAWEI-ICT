package com.ruoyi.platform.domain;

import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 视频分类对象 t_video_category
 *
 * @author ruoyi
 */
@Data
public class VideoCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 删除状态 */
    @Excel(name = "删除状态")
    private Integer delStatus;
}
