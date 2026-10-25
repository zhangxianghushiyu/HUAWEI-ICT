package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    //支付状态（1：未支付，2：已支付，3：已退款）

    STATUS_1(1, "未支付"),

    STATUS_2(2, "已支付"),

    STATUS_3(3, "已退款");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (PayStatusEnum e : PayStatusEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
