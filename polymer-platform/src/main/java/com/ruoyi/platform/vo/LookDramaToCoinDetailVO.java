package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "看剧领金币详细返回对象")
public class LookDramaToCoinDetailVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "金额")
    private BigDecimal price;

    @ApiModelProperty(value = "分钟字符串")
    private String minuteStr;

    @ApiModelProperty(value = "秒")
    private Integer secondInfo;

    @ApiModelProperty(value = "是否领取（0：未领取，1：已领取）")
    private Integer isReceive=0;


    @JsonIgnore
    @ApiModelProperty(value = "领取记录ID")
    private Integer receiveRecordId;
}
