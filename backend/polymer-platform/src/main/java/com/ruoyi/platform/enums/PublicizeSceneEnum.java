package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum PublicizeSceneEnum {


    TYPE_1(1, "解锁短剧"),
    TYPE_2(2, "通关奖励"),
    TYPE_3(3, "幸运奖励"),
    TYPE_4(4, "红包奖励"),
    TYPE_5(5, "小说奖励"),
    TYPE_6(6, "广告奖励"),
    TYPE_7(7, "解锁视频");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (PublicizeSceneEnum e : PublicizeSceneEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
