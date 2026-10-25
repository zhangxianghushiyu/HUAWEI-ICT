package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.TUserRole;

import java.util.List;

/**
 * 用户身份Service接口
 * 
 * @author ruoyi
 * @date 2025-07-02
 */
public interface ITUserRoleService 
{
    /**
     * 查询用户身份
     * 
     * @param id 用户身份主键
     * @return 用户身份
     */
    public TUserRole selectTUserRoleById(Long id);

    /**
     * 查询用户身份列表
     * 
     * @param tUserRole 用户身份
     * @return 用户身份集合
     */
    public List<TUserRole> selectTUserRoleList(TUserRole tUserRole);

    /**
     * 新增用户身份
     * 
     * @param tUserRole 用户身份
     * @return 结果
     */
    public int insertTUserRole(TUserRole tUserRole);

    /**
     * 修改用户身份
     * 
     * @param tUserRole 用户身份
     * @return 结果
     */
    public int updateTUserRole(TUserRole tUserRole);

    /**
     * 批量删除用户身份
     * 
     * @param ids 需要删除的用户身份主键集合
     * @return 结果
     */
    public int deleteTUserRoleByIds(Long[] ids);

    /**
     * 删除用户身份信息
     * 
     * @param id 用户身份主键
     * @return 结果
     */
    public int deleteTUserRoleById(Long id);
}
