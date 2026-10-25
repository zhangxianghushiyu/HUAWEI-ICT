package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(description = "提现选项卡信息返回对象")
public class WithdrawalTabInfoVO implements Serializable {

    @ApiModelProperty(value = "支付宝选项卡")
    private List<WithdrawalTabVO> aliWithdrawalTab;

    @ApiModelProperty(value = "微信选项卡")
    private List<WithdrawalTabVO> weiWithdrawalTab;

}
