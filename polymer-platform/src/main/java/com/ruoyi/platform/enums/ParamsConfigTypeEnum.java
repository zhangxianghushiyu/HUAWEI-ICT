package com.ruoyi.platform.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ParamsConfigTypeEnum {


    TYPE_1(1, "阿里云短信"),
    TYPE_2(2, "腾讯云对象存储"),
    TYPE_3(3, "微信相关配置"),
    TYPE_4(4, "支付宝相关配置"),
    TYPE_5(5, "客户端默认头像"),
    TYPE_6(6, "客户端默认昵称"),
    TYPE_7(7, "客户端默认密码"),
    TYPE_8(8, "回调地址域名（微信支付、支付宝支付）"),
    TYPE_10(10, "每位用户每天查看广告限制次数"),
    TYPE_11(11, "解锁短剧是否赠送金币（1：赠送，2：不赠送）");


    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (ParamsConfigTypeEnum e : ParamsConfigTypeEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }
}
