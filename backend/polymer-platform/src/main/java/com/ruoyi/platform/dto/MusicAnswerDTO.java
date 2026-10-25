package com.ruoyi.platform.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value="歌曲答题入参对象")
public class MusicAnswerDTO implements Serializable {

    @NotNull(message = "歌曲ID不能为空！")
    @ApiModelProperty(value="歌曲ID")
    private Integer id;

    @NotNull(message = "关卡不能为空！")
    @ApiModelProperty(value="关卡")
    private Integer barrier;

    @NotBlank(message = "提交结果字符串不能为空！")
    @ApiModelProperty(value="提交结果字符串")
    private String answer;
}
