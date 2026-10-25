package com.ruoyi.platform.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.GradeConfig;
import com.ruoyi.platform.mapper.GradeConfigMapper;
import com.ruoyi.platform.service.IGradeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户阶级配置Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-10
 */
@Service
public class GradeConfigServiceImpl implements IGradeConfigService {
    @Autowired
    private GradeConfigMapper gradeConfigMapper;

    /**
     * 查询用户阶级配置
     *
     * @param id 用户阶级配置主键
     * @return 用户阶级配置
     */
    @Override
    public GradeConfig selectGradeConfigById(Long id) {
        return gradeConfigMapper.selectGradeConfigById(id);
    }

    /**
     * 查询用户阶级配置列表
     *
     * @param gradeConfig 用户阶级配置
     * @return 用户阶级配置
     */
    @Override
    public List<GradeConfig> selectGradeConfigList(GradeConfig gradeConfig) {
        return gradeConfigMapper.selectGradeConfigList(gradeConfig);
    }

    /**
     * 新增用户阶级配置
     *
     * @param gradeConfig 用户阶级配置
     * @return 结果
     */
    @Override
    public int insertGradeConfig(GradeConfig gradeConfig) {
        gradeConfig.setCreateTime(DateUtils.getNowDate());
        return gradeConfigMapper.insertGradeConfig(gradeConfig);
    }

    /**
     * 修改用户阶级配置
     *
     * @param gradeConfig 用户阶级配置
     * @return 结果
     */
    @Override
    public int updateGradeConfig(GradeConfig gradeConfig) {
        gradeConfig.setUpdateTime(DateUtils.getNowDate());
        return gradeConfigMapper.updateGradeConfig(gradeConfig);
    }

    /**
     * 批量删除用户阶级配置
     *
     * @param ids 需要删除的用户阶级配置主键
     * @return 结果
     */
    @Override
    public int deleteGradeConfigByIds(Long[] ids) {
        return gradeConfigMapper.deleteGradeConfigByIds(ids);
    }

    /**
     * 删除用户阶级配置信息
     *
     * @param id 用户阶级配置主键
     * @return 结果
     */
    @Override
    public int deleteGradeConfigById(Long id) {
        return gradeConfigMapper.deleteGradeConfigById(id);
    }

    @Override
    public GradeConfig selectGradeConfigByGrade(Integer grade) {
        return gradeConfigMapper.selectGradeConfigByGrade(grade);
    }
}
