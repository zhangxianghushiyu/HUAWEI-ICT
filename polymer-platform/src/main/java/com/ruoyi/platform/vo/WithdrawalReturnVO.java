package com.ruoyi.platform.vo;

import com.ruoyi.platform.domain.wx.TransferBillsResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "提现信息返回对象")
public class WithdrawalReturnVO implements Serializable {

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "微信返回参数")
    private TransferBillsResult transferBillsResult;
}
