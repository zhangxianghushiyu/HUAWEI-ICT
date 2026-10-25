package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.ParamsConfig;

import java.util.List;

public interface ParamsConfigService {

    /**
     * 根据类型查询
     */
    List<ParamsConfig> getParamsList(Integer type);

    /**
     * 根据副类型查询
     */
    ParamsConfig getFollowParams(List<ParamsConfig> paramsConfigs, Integer followType);
}
