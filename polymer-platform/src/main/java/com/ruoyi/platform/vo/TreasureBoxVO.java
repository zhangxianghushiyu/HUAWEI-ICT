package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "宝盒信息返回对象")
public class TreasureBoxVO implements Serializable {


    @ApiModelProperty(value = "今日是否领取（0：未领取，1：已领取）")
    private Integer todayIsReceive;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "可以领取的时间")
    private Date time;


    @ApiModelProperty(value = "间隔秒数")
    private Integer seconds;




}
