package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.Version;

/**
 * 版本信息Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
public interface VersionMapper 
{
    /**
     * 查询版本信息
     * 
     * @param id 版本信息主键
     * @return 版本信息
     */
    public Version selectVersionById(Long id);

    /**
     * 查询版本信息列表
     * 
     * @param version 版本信息
     * @return 版本信息集合
     */
    public List<Version> selectVersionList(Version version);

    /**
     * 新增版本信息
     * 
     * @param version 版本信息
     * @return 结果
     */
    public int insertVersion(Version version);

    /**
     * 修改版本信息
     * 
     * @param version 版本信息
     * @return 结果
     */
    public int updateVersion(Version version);

    /**
     * 删除版本信息
     * 
     * @param id 版本信息主键
     * @return 结果
     */
    public int deleteVersionById(Long id);

    /**
     * 批量删除版本信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVersionByIds(Long[] ids);
}
