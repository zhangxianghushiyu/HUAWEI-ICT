package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value="金币明细返回对象")
public class BillGoldListVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Integer id;

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

}
