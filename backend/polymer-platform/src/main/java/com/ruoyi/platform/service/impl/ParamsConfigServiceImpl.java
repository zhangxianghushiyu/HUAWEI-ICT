package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.mapper.ParamsConfigDao;
import com.ruoyi.platform.service.ParamsConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ParamsConfigServiceImpl implements ParamsConfigService {

    @Autowired
    private ParamsConfigDao paramsConfigDao;

    @Override
    public List<ParamsConfig> getParamsList(Integer type) {
        return paramsConfigDao.selectParamsConfigListByType(type);
    }


    @Override
    public ParamsConfig getFollowParams(List<ParamsConfig> paramsConfigs, Integer followType) {
        return paramsConfigs.stream()
                .filter(item -> followType.equals(item.getFollowType()))
                .collect(Collectors.toList()).get(0);
    }
}
