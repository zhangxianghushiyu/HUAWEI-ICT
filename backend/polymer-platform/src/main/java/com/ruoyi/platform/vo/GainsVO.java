package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "收益vo")
public class GainsVO {

    @ApiModelProperty(value = "今日收益")
    private Integer dayGains = 0;

    @ApiModelProperty(value = "总收益")
    private Integer totalGains = 0;

    @ApiModelProperty(value = "团队总人数")
    private Integer teamNums = 0;
}
