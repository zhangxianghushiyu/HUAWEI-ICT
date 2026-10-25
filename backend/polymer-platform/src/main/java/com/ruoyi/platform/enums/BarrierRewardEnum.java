package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum BarrierRewardEnum {


    TYPE_0(0, "无奖励"),
    TYPE_1(1, "通关奖励"),
    TYPE_2(2, "幸运奖励"),
    TYPE_4(4, "群红包");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (BarrierRewardEnum e : BarrierRewardEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
