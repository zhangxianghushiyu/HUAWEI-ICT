package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "新人任务返回对象")
public class NewPeopleTaskListVO {

    @ApiModelProperty(value = "见面领现金返回对象")
    private MeetToReceiveCashVO meetToReceiveCashVO;

    @ApiModelProperty(value = "新人七日签到返回对象")
    private NewcomersSignInForSevenDaysVO newcomersSignInForSevenDaysVO;

    @ApiModelProperty(value = "提现一元返回对象")
    private WithdrawOneYuanVO withdrawOneYuanVO;

    @ApiModelProperty(value = "新人看剧领现金返回对象")
    private NewPeopleLookToDramasCashVO newPeopleLookToDramasCashVO;

}
