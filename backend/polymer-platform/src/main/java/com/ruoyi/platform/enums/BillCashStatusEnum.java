package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum BillCashStatusEnum {


    STATUS_0(0, "审核中"),
    STATUS_1(1, "已打款"),
    STATUS_2(2, "已驳回"),
    STATUS_3(3, "已通过");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (BillCashStatusEnum e : BillCashStatusEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
