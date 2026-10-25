package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value="TaKu广告回调出参对象")
public class TaKuAdCallbackVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private Integer id;

    /**
     * 金额
     */
    @ApiModelProperty(value="金额")
    private BigDecimal money;

    /**
     * 是否已解锁短剧
     */
    @ApiModelProperty(value="是否已解锁短剧（1：已解锁，2：未解锁）")
    private Integer isUnlock;

}
