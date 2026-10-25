package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="收货地址返回对象")
public class UserReceivingAddressVO implements Serializable {

    @ApiModelProperty(value="主键")
    private Integer id;


    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 所在城市
     */
    @ApiModelProperty(value="所在城市")
    private String city;

    /**
     * 收货地址
     */
    @ApiModelProperty(value="收货地址")
    private String address;
}
