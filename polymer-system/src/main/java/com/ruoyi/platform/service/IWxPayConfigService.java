package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.dto.AliPayConfigListDTO;
import com.ruoyi.platform.dto.WxPayConfigListDTO;
import com.ruoyi.platform.vo.AliPayConfigListVO;
import com.ruoyi.platform.vo.WxPayConfigListVO;

import java.util.List;

public interface IWxPayConfigService {

    /**
     * 获取支付宝配置
     */
    List<WxPayConfigListVO> getWxPayConfigListVO(WxPayConfigListDTO dto);

    /**
     * 获取信息
     */
    ParamsConfig getParamsConfigInfo(Long id);
}
