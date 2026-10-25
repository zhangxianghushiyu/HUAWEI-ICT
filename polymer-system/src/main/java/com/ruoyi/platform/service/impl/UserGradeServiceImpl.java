package com.ruoyi.platform.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.UserGrade;
import com.ruoyi.platform.mapper.UserGradeMapper;
import com.ruoyi.platform.service.IUserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户阶级记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
@Service
public class UserGradeServiceImpl implements IUserGradeService 
{
    @Autowired
    private UserGradeMapper userGradeMapper;

    /**
     * 查询用户阶级记录
     * 
     * @param id 用户阶级记录主键
     * @return 用户阶级记录
     */
    @Override
    public UserGrade selectUserGradeById(Long id)
    {
        return userGradeMapper.selectUserGradeById(id);
    }

    /**
     * 查询用户阶级记录列表
     * 
     * @param userGrade 用户阶级记录
     * @return 用户阶级记录
     */
    @Override
    public List<UserGrade> selectUserGradeList(UserGrade userGrade)
    {
        return userGradeMapper.selectUserGradeList(userGrade);
    }

    /**
     * 新增用户阶级记录
     * 
     * @param userGrade 用户阶级记录
     * @return 结果
     */
    @Override
    public int insertUserGrade(UserGrade userGrade)
    {
        userGrade.setCreateTime(DateUtils.getNowDate());
        return userGradeMapper.insertUserGrade(userGrade);
    }

    /**
     * 修改用户阶级记录
     * 
     * @param userGrade 用户阶级记录
     * @return 结果
     */
    @Override
    public int updateUserGrade(UserGrade userGrade)
    {
        userGrade.setUpdateTime(DateUtils.getNowDate());
        return userGradeMapper.updateUserGrade(userGrade);
    }

    /**
     * 批量删除用户阶级记录
     * 
     * @param ids 需要删除的用户阶级记录主键
     * @return 结果
     */
    @Override
    public int deleteUserGradeByIds(Long[] ids)
    {
        return userGradeMapper.deleteUserGradeByIds(ids);
    }

    /**
     * 删除用户阶级记录信息
     * 
     * @param id 用户阶级记录主键
     * @return 结果
     */
    @Override
    public int deleteUserGradeById(Long id)
    {
        return userGradeMapper.deleteUserGradeById(id);
    }
}
