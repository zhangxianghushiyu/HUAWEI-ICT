package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.GradeConfig;

import java.util.List;

/**
 * 用户阶级配置Service接口
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public interface IGradeConfigService 
{
    /**
     * 查询用户阶级配置
     * 
     * @param id 用户阶级配置主键
     * @return 用户阶级配置
     */
    public GradeConfig selectGradeConfigById(Long id);

    /**
     * 查询用户阶级配置列表
     * 
     * @param gradeConfig 用户阶级配置
     * @return 用户阶级配置集合
     */
    public List<GradeConfig> selectGradeConfigList(GradeConfig gradeConfig);

    /**
     * 新增用户阶级配置
     * 
     * @param gradeConfig 用户阶级配置
     * @return 结果
     */
    public int insertGradeConfig(GradeConfig gradeConfig);

    /**
     * 修改用户阶级配置
     * 
     * @param gradeConfig 用户阶级配置
     * @return 结果
     */
    public int updateGradeConfig(GradeConfig gradeConfig);

    /**
     * 批量删除用户阶级配置
     * 
     * @param ids 需要删除的用户阶级配置主键集合
     * @return 结果
     */
    public int deleteGradeConfigByIds(Long[] ids);

    /**
     * 删除用户阶级配置信息
     * 
     * @param id 用户阶级配置主键
     * @return 结果
     */
    public int deleteGradeConfigById(Long id);

    /**
     * 查询
     * @return
     */
    GradeConfig selectGradeConfigByGrade(Integer grade);
}
