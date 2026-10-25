package com.ruoyi.platform.service;

import java.util.List;

import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.dto.ClientConfigListDTO;
import com.ruoyi.platform.vo.ClientConfigListVO;

/**
 * 参数配置Service接口
 *
 * @author ruoyi
 * @date 2024-12-07
 */
public interface IParamsConfigService {
    /**
     * 查询参数配置
     *
     * @param id 参数配置主键
     * @return 参数配置
     */
    public ParamsConfig selectParamsConfigById(Long id);

    /**
     * 查询参数配置列表
     *
     * @param paramsConfig 参数配置
     * @return 参数配置集合
     */
    public List<ParamsConfig> selectParamsConfigList(ParamsConfig paramsConfig);

    /**
     * 新增参数配置
     *
     * @param paramsConfig 参数配置
     * @return 结果
     */
    public int insertParamsConfig(ParamsConfig paramsConfig);

    /**
     * 修改参数配置
     *
     * @param paramsConfig 参数配置
     * @return 结果
     */
    public int updateParamsConfig(ParamsConfig paramsConfig);

    /**
     * 批量删除参数配置
     *
     * @param ids 需要删除的参数配置主键集合
     * @return 结果
     */
    public int deleteParamsConfigByIds(Long[] ids);

    /**
     * 删除参数配置信息
     *
     * @param id 参数配置主键
     * @return 结果
     */
    public int deleteParamsConfigById(Long id);


    /**
     * 根据类型查询
     */
    List<ParamsConfig> getParamsList(Integer type);

    /**
     * 根据副类型查询
     */
    ParamsConfig getFollowParams(List<ParamsConfig> paramsConfigs, Integer followType);


    /**
     * 获取客户端部分配置
     * @return
     */
    List<ClientConfigListVO> getClientConfigListVO(ClientConfigListDTO dto);
}
