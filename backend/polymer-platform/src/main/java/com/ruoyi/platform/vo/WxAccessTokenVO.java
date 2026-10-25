package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "微信accessToken返回数据")
@Data
public class WxAccessTokenVO {

    private String accessToken;

    private String openId;

    private String unionId;

    private String refreshToken;

    @ApiModelProperty(value = "第三方应用名称")
    private String nickName;

    @ApiModelProperty(value="第三方应用头像")
    private String headimgurl;

    @ApiModelProperty(value="第三方返回结果")
    private String returnResult;
}
