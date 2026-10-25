package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 广告配置
 * t_ad_config
 */
@Data
@ApiModel(value="平台配置返回对象")
public class TaKuAdConfigVO implements Serializable {

    @ApiModelProperty(value = "激励广告位ID")
    private String motivateId ;

    @ApiModelProperty(value = "开屏广告位ID")
    private String openAdvId ;

    @ApiModelProperty(value = "appId")
    private String taKuAppId;

    @ApiModelProperty(value = "key")
    private String taKuKey;

    @ApiModelProperty(value = "微信APPID")
    private String wxAppId;

    @ApiModelProperty(value = "上方bannerid")
    private String topBannerId;

    @ApiModelProperty(value = "下方bannerid")
    private String bottomBannerId;

    @ApiModelProperty(value = "插屏广告id")
    private String interstitialId;

    @ApiModelProperty(value = "信息流id")
    private String nativeId;

    @ApiModelProperty(value = "ROOT检测是否开启")
    private Integer isRoot;

    @ApiModelProperty(value = "模拟器检测是否开启")
    private Integer isSimulator;
}