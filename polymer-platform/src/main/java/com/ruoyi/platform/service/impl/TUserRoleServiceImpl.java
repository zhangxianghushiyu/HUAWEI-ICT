package com.ruoyi.platform.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.TUserRole;
import com.ruoyi.platform.mapper.TUserRoleDao;
import com.ruoyi.platform.service.ITUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户身份Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-02
 */
@Service
public class TUserRoleServiceImpl implements ITUserRoleService
{
    @Autowired
    private TUserRoleDao tUserRoleMapper;

    /**
     * 查询用户身份
     * 
     * @param id 用户身份主键
     * @return 用户身份
     */
    @Override
    public TUserRole selectTUserRoleById(Long id)
    {
        return tUserRoleMapper.selectTUserRoleById(id);
    }

    /**
     * 查询用户身份列表
     * 
     * @param tUserRole 用户身份
     * @return 用户身份
     */
    @Override
    public List<TUserRole> selectTUserRoleList(TUserRole tUserRole)
    {
        return tUserRoleMapper.selectTUserRoleList(tUserRole);
    }

    /**
     * 新增用户身份
     * 
     * @param tUserRole 用户身份
     * @return 结果
     */
    @Override
    public int insertTUserRole(TUserRole tUserRole)
    {
        tUserRole.setCreateTime(DateUtils.getNowDate());
        return tUserRoleMapper.insertTUserRole(tUserRole);
    }

    /**
     * 修改用户身份
     * 
     * @param tUserRole 用户身份
     * @return 结果
     */
    @Override
    public int updateTUserRole(TUserRole tUserRole)
    {
        tUserRole.setUpdateTime(DateUtils.getNowDate());
        return tUserRoleMapper.updateTUserRole(tUserRole);
    }

    /**
     * 批量删除用户身份
     * 
     * @param ids 需要删除的用户身份主键
     * @return 结果
     */
    @Override
    public int deleteTUserRoleByIds(Long[] ids)
    {
        return tUserRoleMapper.deleteTUserRoleByIds(ids);
    }

    /**
     * 删除用户身份信息
     * 
     * @param id 用户身份主键
     * @return 结果
     */
    @Override
    public int deleteTUserRoleById(Long id)
    {
        return tUserRoleMapper.deleteTUserRoleById(id);
    }
}
