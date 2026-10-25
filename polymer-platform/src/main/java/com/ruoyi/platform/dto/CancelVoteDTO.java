package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 取消投票DTO
 */
@Data
@ApiModel("取消投票请求参数")
public class CancelVoteDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动ID", required = true)
    @NotNull(message = "活动ID不能为空")
    private Integer activityId;
}
