package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.dto.AliInfoConfigListDTO;
import com.ruoyi.platform.dto.AliPayConfigListDTO;
import com.ruoyi.platform.mapper.ParamsConfigMapper;
import com.ruoyi.platform.service.IAliInfoConfigService;
import com.ruoyi.platform.service.IAliPayConfigService;
import com.ruoyi.platform.vo.AliInfoConfigListVO;
import com.ruoyi.platform.vo.AliPayConfigListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliInfoConfigServiceImpl implements IAliInfoConfigService {

    @Autowired
    private ParamsConfigMapper paramsConfigMapper;

    @Override
    public List<AliInfoConfigListVO> getAliInfoConfigListVO(AliInfoConfigListDTO dto) {
        return paramsConfigMapper.selectAliInfoConfigListVO();
    }


    @Override
    public ParamsConfig getParamsConfigInfo(Long id) {
        return paramsConfigMapper.selectParamsConfigById(id);
    }
}
