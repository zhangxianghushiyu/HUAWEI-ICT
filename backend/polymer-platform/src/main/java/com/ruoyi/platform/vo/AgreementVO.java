package com.ruoyi.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="协议信息返回对象")
public class AgreementVO implements Serializable {

    @ApiModelProperty(value = "协议ID")
    private Integer id;

    @ApiModelProperty(value = "类型（1：用户手册，2：隐私政策，3：收集个人信息明示清单，4：个人信息第三方共享清单，5：儿童个人信息保护规则，" +
            "6：青少年文明公约，7：注销协议，8：福利规则，9：会员服务协议，10：推广协议）")
    private Integer type;

    @ApiModelProperty(value = "内容")
    private String content;
}
