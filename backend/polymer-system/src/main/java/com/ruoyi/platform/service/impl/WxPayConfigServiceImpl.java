package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.dto.AliPayConfigListDTO;
import com.ruoyi.platform.dto.WxPayConfigListDTO;
import com.ruoyi.platform.mapper.ParamsConfigMapper;
import com.ruoyi.platform.service.IAliPayConfigService;
import com.ruoyi.platform.service.IWxPayConfigService;
import com.ruoyi.platform.vo.AliPayConfigListVO;
import com.ruoyi.platform.vo.WxPayConfigListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxPayConfigServiceImpl implements IWxPayConfigService {

    @Autowired
    private ParamsConfigMapper paramsConfigMapper;

    @Override
    public List<WxPayConfigListVO> getWxPayConfigListVO(WxPayConfigListDTO dto) {
        return paramsConfigMapper.selectWxPayConfigListVO();
    }

    @Override
    public ParamsConfig getParamsConfigInfo(Long id) {
        return paramsConfigMapper.selectParamsConfigById(id);
    }
}