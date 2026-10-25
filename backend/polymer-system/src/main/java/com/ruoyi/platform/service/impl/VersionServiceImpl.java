package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.VersionMapper;
import com.ruoyi.platform.domain.Version;
import com.ruoyi.platform.service.IVersionService;

/**
 * 版本信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
@Service
public class VersionServiceImpl implements IVersionService 
{
    @Autowired
    private VersionMapper versionMapper;

    /**
     * 查询版本信息
     * 
     * @param id 版本信息主键
     * @return 版本信息
     */
    @Override
    public Version selectVersionById(Long id)
    {
        return versionMapper.selectVersionById(id);
    }

    /**
     * 查询版本信息列表
     * 
     * @param version 版本信息
     * @return 版本信息
     */
    @Override
    public List<Version> selectVersionList(Version version)
    {
        return versionMapper.selectVersionList(version);
    }

    /**
     * 新增版本信息
     * 
     * @param version 版本信息
     * @return 结果
     */
    @Override
    public int insertVersion(Version version)
    {
        version.setCreateTime(DateUtils.getNowDate());
        return versionMapper.insertVersion(version);
    }

    /**
     * 修改版本信息
     * 
     * @param version 版本信息
     * @return 结果
     */
    @Override
    public int updateVersion(Version version)
    {
        version.setUpdateTime(DateUtils.getNowDate());
        return versionMapper.updateVersion(version);
    }

    /**
     * 批量删除版本信息
     * 
     * @param ids 需要删除的版本信息主键
     * @return 结果
     */
    @Override
    public int deleteVersionByIds(Long[] ids)
    {
        return versionMapper.deleteVersionByIds(ids);
    }

    /**
     * 删除版本信息信息
     * 
     * @param id 版本信息主键
     * @return 结果
     */
    @Override
    public int deleteVersionById(Long id)
    {
        return versionMapper.deleteVersionById(id);
    }
}
