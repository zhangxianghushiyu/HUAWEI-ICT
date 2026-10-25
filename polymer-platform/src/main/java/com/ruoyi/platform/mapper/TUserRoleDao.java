package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.TUserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户身份Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-02
 */
@Mapper
public interface TUserRoleDao
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
     * 删除用户身份
     * 
     * @param id 用户身份主键
     * @return 结果
     */
    public int deleteTUserRoleById(Long id);

    /**
     * 批量删除用户身份
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTUserRoleByIds(Long[] ids);
}
