package com.ruoyi.common.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ParamsConfigFollowTypeEnum {


    TYPE_1(1, "阿里短信accessKeyId"),
    TYPE_2(2, "阿里云短信accessKeySecret"),
    TYPE_3(3, "阿里云短信endpoint"),
    TYPE_4(4, "阿里云短信signName"),
    TYPE_5(5, "阿里云短信templateCode"),
    TYPE_6(6, "腾讯云对象存储secretId"),
    TYPE_7(7, "腾讯云对象存储secretKey"),
    TYPE_8(8, "腾讯云对象存储bucketName"),
    TYPE_9(9, "腾讯云对象存储region"),
    TYPE_10(10, "微信appId"),
    TYPE_11(11, "微信appSecret"),
    TYPE_12(12, "微信支付商户号"),
    TYPE_13(13, "微信支付商户密钥"),
    TYPE_14(14, "微信支付p12证书的位置，可以指定绝对路径"),
    TYPE_15(15, "支付宝APPID"),
    TYPE_16(16, "应用私钥"),
    TYPE_17(17, "应用公钥证书路径"),
    TYPE_18(18, "支付宝公钥证书路径"),
    TYPE_19(19, "支付宝根证书路径"),
    TYPE_20(20, "客户端默认头像"),
    TYPE_21(21, "客户端默认昵称"),
    TYPE_22(22, "客户端默认密码"),
    TYPE_23(23, "回调地址域名（微信支付、支付宝支付）"),
    TYPE_24(24, "阿里云OSS（ARM）AccessKeyId"),
    TYPE_25(25, "阿里云OSS（ARM）AccessKeySecret"),
    TYPE_26(26, "阿里云OSS（ARM）endpoint"),
    TYPE_27(27, "阿里云OSS（ARM）RoleArn"),
    TYPE_28(28, "签约的支付宝账号对应的支付宝唯一用户号，以 2088 开头的 16 位纯数字组成"),
    TYPE_29(29, "服务商证书文件，apiclient_key.pem证书文件"),
    TYPE_30(30, "apiclient_cert.pem证书文件"),
    TYPE_31(31, "公钥ID（商户秘钥验签是填写，反之证书验签时填写1）"),
    TYPE_32(32, "pub_key.pem证书文件（商户秘钥验签是填写，反之证书验签时填写1）"),
    TYPE_33(33, "每位用户每天查看广告限制次数"),
    TYPE_34(34, "解锁短剧是否赠送金币（1：赠送，2：不赠送）"),
    TYPE_35(35, "小说激励视频弹出时间（单位：分钟）"),
    TYPE_36(36, "小说信息流广告弹出时间（页数）");

    private final Integer code;

    private final String message;

    /**
     * 根据状态码获取
     */
    public static String codeToMessage(Integer code) {
        if (null != code) {
            for (ParamsConfigFollowTypeEnum e : ParamsConfigFollowTypeEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e.getMessage();
                }
            }
        }
        return "未知";
    }

//    private final Integer type;
//    private final String msg;
//
//    ParamsConfigFollowTypeEnum(Integer type, String msg)
//    {
//        this.type = type;
//        this.msg = msg;
//    }
//
    public Integer getType()
    {
        return code;
    }

    public String getMsg()
    {
        return message;
    }
}
