package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "支付宝accessToken返回数据")
@Data
public class AliAccessTokenVO {
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
