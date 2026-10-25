package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum TaskFollowTypeEnum {


    TYPE_1(1, "见面领现金"),

    TYPE_2(2, "新人签到领金币"),

    TYPE_3(3, "提现一元"),

    TYPE_4(4, "新人看剧领现金"),

    TYPE_5(5, "每日签到"),

    TYPE_6(6, "饭点看剧领补贴"),

    TYPE_7(7, "看剧领金币"),

    TYPE_8(8, "看小视频领金币"),

    TYPE_9(9, "分享领金币"),

    TYPE_10(10, "看指定剧领金币");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (TaskFollowTypeEnum e : TaskFollowTypeEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
