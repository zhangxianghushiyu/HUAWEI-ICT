package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "新人看剧领现金详细返回对象")
public class NewPeopleLookDramaDetailVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "金额")
    private BigDecimal price;

    @ApiModelProperty(value = "天")
    private String dayStr;

    @ApiModelProperty(value = "是否领取（0：未领取，1：已领取）")
    private Integer isReceive=0;


    @JsonIgnore
    @ApiModelProperty(value = "领取记录ID")
    private Integer receiveRecordId;
}
