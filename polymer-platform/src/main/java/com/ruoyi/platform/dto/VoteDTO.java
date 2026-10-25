package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 单选投票DTO
 */
@Data
@ApiModel("单选投票请求参数")
public class VoteDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动ID", required = true)
    @NotNull(message = "活动ID不能为空")
    private Integer activityId;

    @ApiModelProperty(value = "选项ID", required = true)
    @NotNull(message = "选项ID不能为空")
    private Integer optionId;
}
