package com.ruoyi.platform.controller.platform;


import com.ruoyi.platform.service.AgreementService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.AgreementVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "协议信息API")
@RestController
@Slf4j
@RequestMapping("/platform/agreement")
public class AgreementController {

    @Autowired
    private AgreementService agreementService;

    @ApiOperation(value = "获取协议信息")
    @ApiImplicitParam(name = "type", value = "类型（1：用户手册，2：隐私政策，3：收集个人信息明示清单，4：个人信息第三方共享清单，" +
            "5：儿童个人信息保护规则，6：青少年文明公约，7：注销协议，8：福利规则，9：会员服务协议，10：推广协议）", required = true)
    @GetMapping("/get")
    public R<AgreementVO> get(@RequestParam(value = "type") Integer type) {
        AgreementVO agreementVO = agreementService.getAgreementVO(type);
        return R.ok(agreementVO);
    }

}
