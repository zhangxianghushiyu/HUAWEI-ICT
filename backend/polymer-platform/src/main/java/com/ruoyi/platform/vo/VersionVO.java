package com.ruoyi.platform.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@ApiModel(value = "版本信息")
public class VersionVO {


    /**
     * 版本号
     */
    @ApiModelProperty(value="版本号")
    private String versionNo;

    /**
     * 版本说明
     */
    @ApiModelProperty(value="版本说明")
    private String releaseNote;

    /**
     * 下载地址
     */
    @ApiModelProperty(value="下载地址")
    private String appUrl;

    /**
     * 图片地址
     */
    @ApiModelProperty(value="图片地址")
    private String url;


    /**
     * 兑换比例
     */
    @ApiModelProperty(value="兑换比例")
    private Integer convertRatio;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value="创建时间")
    private Date createTime;


}
