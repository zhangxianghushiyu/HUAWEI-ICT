package com.ruoyi.platform.config;

import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.enums.ParamsConfigTypeEnum;
import com.ruoyi.platform.service.ParamsConfigService;
import com.ruoyi.platform.service.WxService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Binary Wang
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@AllArgsConstructor
public class WxPayConfiguration {
    @Autowired
    private ParamsConfigService paramsConfigService;
    @Autowired
    private WxService wxService;

    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxService() {
        List<ParamsConfig> paramsList = paramsConfigService.getParamsList(ParamsConfigTypeEnum.TYPE_3.getCode());
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxService.configWxPayConfig(paramsList));
        return wxPayService;
    }

}
