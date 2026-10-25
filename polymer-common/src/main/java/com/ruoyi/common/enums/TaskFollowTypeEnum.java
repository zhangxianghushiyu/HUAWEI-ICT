package com.ruoyi.common.enums;




public enum TaskFollowTypeEnum {


    TYPE_1(1, "见面领现金"), TYPE_2(2, "新人签到领金币"), TYPE_3(3, "提现一元"),
    TYPE_4(4, "新人看剧领现金"), TYPE_5(5, "每日签到"), TYPE_6(6, "饭点看剧领补贴"),
    TYPE_7(7, "看剧领金币"), TYPE_8(8, "看小视频领金币"), TYPE_9(9, "分享领金币"),
    TYPE_10(10, "看指定剧领金币");



    private final Integer type;
    private final String info;

    TaskFollowTypeEnum(Integer type, String info)
    {
        this.type = type;
        this.info = info;
    }

    public Integer getType()
    {
        return type;
    }

    public String getInfo()
    {
        return info;
    }
}
