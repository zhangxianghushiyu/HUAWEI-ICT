package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 现金场景类型
 */
@Getter
@AllArgsConstructor
public enum BillSceneTypeEnum {


    SCENE_1(1, "加"),

    SCENE_2(2, "减");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (BillSceneTypeEnum e : BillSceneTypeEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
