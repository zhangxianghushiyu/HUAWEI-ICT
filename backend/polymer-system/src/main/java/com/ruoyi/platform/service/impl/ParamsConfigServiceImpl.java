package com.ruoyi.platform.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.dto.ClientConfigListDTO;
import com.ruoyi.platform.vo.ClientConfigListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.ParamsConfigMapper;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.service.IParamsConfigService;

/**
 * 参数配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-12-07
 */
@Service
public class ParamsConfigServiceImpl implements IParamsConfigService {
    @Autowired
    private ParamsConfigMapper paramsConfigMapper;

    /**
     * 查询参数配置
     *
     * @param id 参数配置主键
     * @return 参数配置
     */
    @Override
    public ParamsConfig selectParamsConfigById(Long id) {
        return paramsConfigMapper.selectParamsConfigById(id);
    }

    /**
     * 查询参数配置列表
     *
     * @param paramsConfig 参数配置
     * @return 参数配置
     */
    @Override
    public List<ParamsConfig> selectParamsConfigList(ParamsConfig paramsConfig) {
        return paramsConfigMapper.selectParamsConfigList(paramsConfig);
    }

    /**
     * 新增参数配置
     *
     * @param paramsConfig 参数配置
     * @return 结果
     */
    @Override
    public int insertParamsConfig(ParamsConfig paramsConfig) {
        paramsConfig.setCreateTime(DateUtils.getNowDate());
        return paramsConfigMapper.insertParamsConfig(paramsConfig);
    }

    /**
     * 修改参数配置
     *
     * @param paramsConfig 参数配置
     * @return 结果
     */
    @Override
    public int updateParamsConfig(ParamsConfig paramsConfig) {
        paramsConfig.setUpdateTime(DateUtils.getNowDate());
        return paramsConfigMapper.updateParamsConfig(paramsConfig);
    }

    /**
     * 批量删除参数配置
     *
     * @param ids 需要删除的参数配置主键
     * @return 结果
     */
    @Override
    public int deleteParamsConfigByIds(Long[] ids) {
        return paramsConfigMapper.deleteParamsConfigByIds(ids);
    }

    /**
     * 删除参数配置信息
     *
     * @param id 参数配置主键
     * @return 结果
     */
    @Override
    public int deleteParamsConfigById(Long id) {
        return paramsConfigMapper.deleteParamsConfigById(id);
    }

    @Override
    public List<ParamsConfig> getParamsList(Integer type) {
        return paramsConfigMapper.selectParamsConfigListByType(type);
    }

    @Override
    public ParamsConfig getFollowParams(List<ParamsConfig> paramsConfigs, Integer followType) {
        return paramsConfigs.stream()
                .filter(item -> followType.equals(item.getFollowType()))
                .collect(Collectors.toList()).get(0);
    }


    @Override
    public List<ClientConfigListVO> getClientConfigListVO(ClientConfigListDTO dto) {
        return paramsConfigMapper.selectClientConfigListVO();
    }
}
