package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "见面领现金返回对象")
public class MeetToReceiveCashVO {

    @ApiModelProperty(value = "任务id")
    private Integer id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String describe;

    @ApiModelProperty(value = "奖励类型（1：金币，2：现金，3：人民币）")
    private Integer rewardType;

    @ApiModelProperty(value = "任务类型(1:新手任务,2:日常任务)")
    private Integer type;

    @ApiModelProperty(value = "任务副类型（1：见面领现金，2：新人签到领金币，3：提现一元，4：新人看剧送现金，5：每日签到，6：饭点看剧领补贴，7：看剧领金币，8：看小视频领金币，9：分享领金币，10：看下方剧领金币）")
    private Integer followType;

    @ApiModelProperty(value = "金额")
    private BigDecimal price;
}
