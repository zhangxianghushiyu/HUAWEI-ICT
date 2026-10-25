package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.vo.AliInfoConfigListVO;
import com.ruoyi.platform.vo.AliPayConfigListVO;
import com.ruoyi.platform.vo.ClientConfigListVO;
import com.ruoyi.platform.vo.WxPayConfigListVO;
import org.apache.ibatis.annotations.Param;

/**
 * 参数配置Mapper接口
 * 
 * @author ruoyi
 * @date 2024-12-07
 */
public interface ParamsConfigMapper 
{
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
     * 删除参数配置
     * 
     * @param id 参数配置主键
     * @return 结果
     */
    public int deleteParamsConfigById(Long id);

    /**
     * 批量删除参数配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteParamsConfigByIds(Long[] ids);


    /**
     * 根据类型获取信息
     */
    List<ParamsConfig> selectParamsConfigListByType(@Param("type") Integer type);


    /**
     * 获取支付宝相关配置
     */
    List<AliPayConfigListVO> selectAliPayConfigListVO();

    /**
     * 获取阿里相关配置
     */
    List<AliInfoConfigListVO> selectAliInfoConfigListVO();


    /**
     * 获取微信相关配置
     */
    List<WxPayConfigListVO> selectWxPayConfigListVO();


    /**
     * 获取客户端相关配置
     */
    List<ClientConfigListVO> selectClientConfigListVO();
}
