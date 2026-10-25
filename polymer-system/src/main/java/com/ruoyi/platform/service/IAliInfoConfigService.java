package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.dto.AliInfoConfigListDTO;
import com.ruoyi.platform.vo.AliInfoConfigListVO;

import java.util.List;

public interface IAliInfoConfigService {

    /**
     * 获取支付宝配置
     */
    List<AliInfoConfigListVO> getAliInfoConfigListVO(AliInfoConfigListDTO dto);

    /**
     * 获取信息
     */
    ParamsConfig getParamsConfigInfo(Long id);
}
