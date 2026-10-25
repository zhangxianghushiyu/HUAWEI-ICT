package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "每日任务返回对象")
public class DailyTaskListVO {

//    @JsonIgnore
    @ApiModelProperty(value = "每日签到返回对象")
    private DailySignVO dailySignVO;

    @ApiModelProperty(value = "饭点看剧领补贴返回对象")
    private FoodPointLookDramaVO foodPointLookDrama;

    @ApiModelProperty(value = "看剧领金币返回对象")
    private LookDramaToCoinVO lookDramaToCoinVO;

    @ApiModelProperty(value = "看小视频领金币返回对象")
    private LookVideoToCoinVO lookVideoToCoinVO;

    @ApiModelProperty(value = "分享领金币返回对象")
    private ShareToCoinVO shareToCoinVO;

    @ApiModelProperty(value = "看指定剧领金币返回对象")
    private AppointDramaCoinVO appointDramaCoinVO;


}
