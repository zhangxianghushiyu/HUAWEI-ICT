package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "每日七日签到详细返回对象")
public class DailySignInForSevenDaysDetailVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "金额")
    private BigDecimal price;

    @ApiModelProperty(value = "天")
    private String dayStr;

    @ApiModelProperty(value = "是否签到（0：未签到，1：已签到）")
    private Integer isSign=0;


    @JsonIgnore
    @ApiModelProperty(value = "签到记录ID")
    private Integer signRecordId;
}
