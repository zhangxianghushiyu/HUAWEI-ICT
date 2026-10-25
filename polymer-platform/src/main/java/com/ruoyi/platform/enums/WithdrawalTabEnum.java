package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 提现选项卡类型
 */
@Getter
@AllArgsConstructor
public enum WithdrawalTabEnum {


    WEI(1, "微信提现"),

    ALI(2, "支付宝提现");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (WithdrawalTabEnum e : WithdrawalTabEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
