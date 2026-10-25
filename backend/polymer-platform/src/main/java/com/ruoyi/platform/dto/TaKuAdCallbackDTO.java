package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="TaKu广告回调入参对象")
public class TaKuAdCallbackDTO implements Serializable {

    @ApiModelProperty(value="用户ID")
    private String user_id;

    @ApiModelProperty(value="用户业务参数")
    private String extra_data;

    @ApiModelProperty(value="Taku服务端生成的trans_id，具有唯一性")
    private String trans_id;

    @ApiModelProperty(value="激励名称")
    private String reward_name;

    @ApiModelProperty(value="激励数量")
    private String reward_amount;

    @ApiModelProperty(value="Taku广告位ID")
    private String placement_id;

    @ApiModelProperty(value="Taku聚合的广告平台ID")
    private String network_firm_id;

    @ApiModelProperty(value="Taku广告源ID")
    private String adsource_id;

    @ApiModelProperty(value="Taku广告场景ID")
    private String scenario_id;

    @ApiModelProperty(value="开发者应用的包名或BundleID")
    private String package_name;

    @ApiModelProperty(value="系统平台，Android 或 iOS")
    private String platform;

    @ApiModelProperty(value="Taku SDK回调的展示级别数据. 数据为json字符串")
    private String ilrd;

    @ApiModelProperty(value="签名信息")
    private String sign;

    @ApiModelProperty(value="是否为测试")
    private String is_test;


    @Override
    public String toString() {
        return "TaKuAdCallbackDTO{" +
                "user_id='" + user_id + '\'' +
                ", extra_data='" + extra_data + '\'' +
                ", trans_id='" + trans_id + '\'' +
                ", reward_name='" + reward_name + '\'' +
                ", reward_amount='" + reward_amount + '\'' +
                ", placement_id='" + placement_id + '\'' +
                ", network_firm_id='" + network_firm_id + '\'' +
                ", adsource_id='" + adsource_id + '\'' +
                ", scenario_id='" + scenario_id + '\'' +
                ", package_name='" + package_name + '\'' +
                ", platform='" + platform + '\'' +
                ", ilrd='" + ilrd + '\'' +
                ", sign='" + sign + '\'' +
                ", is_test='" + is_test + '\'' +
                '}';
    }
}
