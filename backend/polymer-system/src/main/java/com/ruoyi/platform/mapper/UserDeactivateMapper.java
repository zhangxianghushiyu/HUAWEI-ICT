package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.UserDeactivate;

/**
 * 用户停用记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
public interface UserDeactivateMapper 
{
    /**
     * 查询用户停用记录
     * 
     * @param id 用户停用记录主键
     * @return 用户停用记录
     */
    public UserDeactivate selectUserDeactivateById(Long id);

    /**
     * 查询用户停用记录列表
     * 
     * @param userDeactivate 用户停用记录
     * @return 用户停用记录集合
     */
    public List<UserDeactivate> selectUserDeactivateList(UserDeactivate userDeactivate);

    /**
     * 新增用户停用记录
     * 
     * @param userDeactivate 用户停用记录
     * @return 结果
     */
    public int insertUserDeactivate(UserDeactivate userDeactivate);

    /**
     * 修改用户停用记录
     * 
     * @param userDeactivate 用户停用记录
     * @return 结果
     */
    public int updateUserDeactivate(UserDeactivate userDeactivate);

    /**
     * 删除用户停用记录
     * 
     * @param id 用户停用记录主键
     * @return 结果
     */
    public int deleteUserDeactivateById(Long id);

    /**
     * 批量删除用户停用记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserDeactivateByIds(Long[] ids);
}
