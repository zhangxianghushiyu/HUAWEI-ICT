package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.UserDeactivateMapper;
import com.ruoyi.platform.domain.UserDeactivate;
import com.ruoyi.platform.service.IUserDeactivateService;

/**
 * 用户停用记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
@Service
public class UserDeactivateServiceImpl implements IUserDeactivateService 
{
    @Autowired
    private UserDeactivateMapper userDeactivateMapper;

    /**
     * 查询用户停用记录
     * 
     * @param id 用户停用记录主键
     * @return 用户停用记录
     */
    @Override
    public UserDeactivate selectUserDeactivateById(Long id)
    {
        return userDeactivateMapper.selectUserDeactivateById(id);
    }

    /**
     * 查询用户停用记录列表
     * 
     * @param userDeactivate 用户停用记录
     * @return 用户停用记录
     */
    @Override
    public List<UserDeactivate> selectUserDeactivateList(UserDeactivate userDeactivate)
    {
        return userDeactivateMapper.selectUserDeactivateList(userDeactivate);
    }

    /**
     * 新增用户停用记录
     * 
     * @param userDeactivate 用户停用记录
     * @return 结果
     */
    @Override
    public int insertUserDeactivate(UserDeactivate userDeactivate)
    {
        userDeactivate.setCreateTime(DateUtils.getNowDate());
        return userDeactivateMapper.insertUserDeactivate(userDeactivate);
    }

    /**
     * 修改用户停用记录
     * 
     * @param userDeactivate 用户停用记录
     * @return 结果
     */
    @Override
    public int updateUserDeactivate(UserDeactivate userDeactivate)
    {
        userDeactivate.setUpdateTime(DateUtils.getNowDate());
        return userDeactivateMapper.updateUserDeactivate(userDeactivate);
    }

    /**
     * 批量删除用户停用记录
     * 
     * @param ids 需要删除的用户停用记录主键
     * @return 结果
     */
    @Override
    public int deleteUserDeactivateByIds(Long[] ids)
    {
        return userDeactivateMapper.deleteUserDeactivateByIds(ids);
    }

    /**
     * 删除用户停用记录信息
     * 
     * @param id 用户停用记录主键
     * @return 结果
     */
    @Override
    public int deleteUserDeactivateById(Long id)
    {
        return userDeactivateMapper.deleteUserDeactivateById(id);
    }
}
