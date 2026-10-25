
package com.ruoyi.platform.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value="收货地址修改入参对象")
public class UserReceivingAddressModifyDTO implements Serializable {


    @NotNull(message = "ID不能为空！")
    @ApiModelProperty(value="ID")
    private Integer id;


    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空！")
    @ApiModelProperty(value="姓名")
    private String name;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空！")
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 所在城市
     */
    @NotBlank(message = "所在城市不能为空！")
    @ApiModelProperty(value="所在城市")
    private String city;

    /**
     * 收货地址
     */
    @NotBlank(message = "收货地址不能为空！")
    @ApiModelProperty(value="收货地址")
    private String address;
}
