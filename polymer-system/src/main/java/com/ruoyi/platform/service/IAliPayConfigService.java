package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.dto.AliPayConfigListDTO;
import com.ruoyi.platform.vo.AliPayConfigListVO;

import java.util.List;

public interface IAliPayConfigService {

    /**
     * 获取支付宝配置
     */
    List<AliPayConfigListVO> getAliPayConfigListVO(AliPayConfigListDTO dto);

    /**
     * 获取信息
     */
    ParamsConfig getParamsConfigInfo(Long id);
}
