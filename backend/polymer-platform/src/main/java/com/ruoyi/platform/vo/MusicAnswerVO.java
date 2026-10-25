package com.ruoyi.platform.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "歌曲答题返回对象")
public class MusicAnswerVO implements Serializable {

    @ApiModelProperty(value = "0：回答错误，1：回答正确")
    private Integer resultCode;

    @ApiModelProperty(value = "0：不过关，1：已过关")
    private Integer isTraverse;

    @ApiModelProperty(value = "奖励类型：0：无奖励，1：通关奖励，2：幸运奖励")
    private Integer rewardType = 0;

    @ApiModelProperty(value = "答题的关卡")
    private Integer barrier;

}
