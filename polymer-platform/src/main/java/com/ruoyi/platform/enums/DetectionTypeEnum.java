package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum DetectionTypeEnum {


    TYPE_1(1, "手机是否ROOT检测"),
    TYPE_2(2, "模拟器检测"),
    TYPE_3(3, "两次广告间隔时间检测"),
    TYPE_4(4, "单次收益大于某个金额检测"),
    TYPE_5(5, "连续N条收益低于某个金额检测"),
    TYPE_6(6, "同一个IP地址N个账号使用检测"),
    TYPE_7(7, "每个用户每天激励广告限制数量");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (DetectionTypeEnum e : DetectionTypeEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
