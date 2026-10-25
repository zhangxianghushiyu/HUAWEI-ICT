package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(description = "用户提现绑定信息返回对象")
public class WithdrawalUserVO implements Serializable {


    @ApiModelProperty(value = "微信：是否绑定（0：未绑定，1：已绑定）")
    private Integer wxIsBind=0;

    @ApiModelProperty(value = "支付宝：是否绑定（0：未绑定，1：已绑定）")
    private Integer aliIsBind=0;


}