package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 活动报名入参对象
 */
@Data
@ApiModel(value = "活动报名入参对象")
public class ActivitySignupDTO implements Serializable {

    @NotNull(message = "活动ID不能为空")
    @ApiModelProperty(value = "活动ID", required = true)
    private Long activityId;

}
