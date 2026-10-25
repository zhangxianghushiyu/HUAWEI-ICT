package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.GradeConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户阶级配置Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-10
 */
public interface GradeConfigMapper {
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
     * 删除用户阶级配置
     *
     * @param id 用户阶级配置主键
     * @return 结果
     */
    public int deleteGradeConfigById(Long id);

    /**
     * 批量删除用户阶级配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGradeConfigByIds(Long[] ids);

    /**
     * 查看是否存在相同的配置
     */
    GradeConfig selectGradeConfigByGrade(@Param("grade") Integer grade);
}
