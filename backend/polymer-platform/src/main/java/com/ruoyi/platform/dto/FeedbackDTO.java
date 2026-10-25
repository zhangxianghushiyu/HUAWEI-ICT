package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 帮助反馈信息
 * t_feedback
 */
@Data
@ApiModel(value="帮助反馈信息入参对象")
public class FeedbackDTO implements Serializable {

    @NotBlank(message = "内容不能为空！")
    @ApiModelProperty(value="内容")
    private String content;

    @NotBlank(message = "手机号不能为空！")
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "请输入正确的手机号")
    @ApiModelProperty(value="手机号")
    private String phone;

    @ApiModelProperty(value="图片集合")
    private List<String> imgUrls;
}