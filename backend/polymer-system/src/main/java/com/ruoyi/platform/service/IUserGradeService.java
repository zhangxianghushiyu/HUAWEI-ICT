package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.UserGrade;

import java.util.List;

/**
 * 用户阶级记录Service接口
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public interface IUserGradeService 
{
    /**
     * 查询用户阶级记录
     * 
     * @param id 用户阶级记录主键
     * @return 用户阶级记录
     */
    public UserGrade selectUserGradeById(Long id);

    /**
     * 查询用户阶级记录列表
     * 
     * @param userGrade 用户阶级记录
     * @return 用户阶级记录集合
     */
    public List<UserGrade> selectUserGradeList(UserGrade userGrade);

    /**
     * 新增用户阶级记录
     * 
     * @param userGrade 用户阶级记录
     * @return 结果
     */
    public int insertUserGrade(UserGrade userGrade);

    /**
     * 修改用户阶级记录
     * 
     * @param userGrade 用户阶级记录
     * @return 结果
     */
    public int updateUserGrade(UserGrade userGrade);

    /**
     * 批量删除用户阶级记录
     * 
     * @param ids 需要删除的用户阶级记录主键集合
     * @return 结果
     */
    public int deleteUserGradeByIds(Long[] ids);

    /**
     * 删除用户阶级记录信息
     * 
     * @param id 用户阶级记录主键
     * @return 结果
     */
    public int deleteUserGradeById(Long id);
}
