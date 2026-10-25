package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value="现金明细返回对象")
public class BillCashListVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 提现方式（1：微信，2：支付宝）
     */
    @ApiModelProperty(value="提现方式（1：微信，2：支付宝）")
    private Integer method;

    /**
     * 场景（1：加，2：减）
     */
    @ApiModelProperty(value="场景（1：加，2：减）")
    private Integer scene;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String describe;
    /**
     * 金额
     */
    @ApiModelProperty(value="金额")
    private BigDecimal money;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    @ApiModelProperty(value="状态（0：审核中，1：已打款，2：已驳回,3：已通过）")
    private Integer status;

    @ApiModelProperty(value="场景（1：系统，2：提现）")
    private Integer take;

    @ApiModelProperty(value="商户号")
    private String mchId;

    @ApiModelProperty(value="微信APPID")
    private String appId;

    @ApiModelProperty(value="微信packageInfo")
    private String packageInfo;

}
