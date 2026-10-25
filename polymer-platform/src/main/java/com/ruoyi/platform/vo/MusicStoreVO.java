package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 曲库信息
 * t_music_store
 */
@Data
public class MusicStoreVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "ID")
    private Integer id;


    /**
     * 答案
     */
    @JsonIgnore
    @ApiModelProperty(value = "答案")
    private String answer;

    /**
     * 错误
     */
    @JsonIgnore
    @ApiModelProperty(value = "错误")
    private String wrong;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String url;


    /**
     * 选项1
     */
    @ApiModelProperty(value = "选项1")
    private String option1;


    /**
     * 选项2
     */
    @ApiModelProperty(value = "选项2")
    private String option2;

    /**
     * 当前关卡
     */
    @ApiModelProperty(value = "当前关卡")
    private Integer barrier;

    /**
     * 当前关卡答对通关数量
     */
    @ApiModelProperty(value = "当前关卡答对通关数量")
    private Integer number;

    /**
     * 当前关卡已经答对数量
     */
    @ApiModelProperty(value = "当前关卡已经答对数量")
    private Integer oldNumber;

    @ApiModelProperty(value = "是否为最高关（0：否，1：是）")
    private Integer maxBarrier;

    @ApiModelProperty(value = "需要提现的关卡（0：不需要提现，1：关卡1,2：关卡2）")
    private Integer isWithdrawalBarrier = 0;

}