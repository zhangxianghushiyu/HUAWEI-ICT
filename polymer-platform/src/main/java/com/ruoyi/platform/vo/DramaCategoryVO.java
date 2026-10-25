package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value = "短剧分类返回对象")
public class DramaCategoryVO implements Serializable {

    /**
     * 分类ID
     */
    @ApiModelProperty(value = "分类ID")
    private Integer classifyId;

    @ApiModelProperty(value = "名称")
    private String name;


}
