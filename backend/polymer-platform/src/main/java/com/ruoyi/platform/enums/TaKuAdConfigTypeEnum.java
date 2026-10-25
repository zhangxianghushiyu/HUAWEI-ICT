package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum TaKuAdConfigTypeEnum {


    TYPE_1(1, "开屏广告"),
    TYPE_2(2, "激励视频"),
    TYPE_3(3, "appId"),
    TYPE_4(4, "key"),
    TYPE_5(5, "上方bannerid"),
    TYPE_6(6, "下方bannerid"),
    TYPE_7(7, "插屏广告id"),
    TYPE_8(8, "信息流id");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (TaKuAdConfigTypeEnum e : TaKuAdConfigTypeEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
