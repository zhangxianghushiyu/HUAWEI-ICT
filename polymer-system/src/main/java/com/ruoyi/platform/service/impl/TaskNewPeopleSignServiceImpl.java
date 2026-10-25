package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaskNewPeopleSignMapper;
import com.ruoyi.platform.domain.TaskNewPeopleSign;
import com.ruoyi.platform.service.ITaskNewPeopleSignService;

/**
 * 新人七日签到配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class TaskNewPeopleSignServiceImpl implements ITaskNewPeopleSignService 
{
    @Autowired
    private TaskNewPeopleSignMapper taskNewPeopleSignMapper;

    /**
     * 查询新人七日签到配置
     * 
     * @param id 新人七日签到配置主键
     * @return 新人七日签到配置
     */
    @Override
    public TaskNewPeopleSign selectTaskNewPeopleSignById(Long id)
    {
        return taskNewPeopleSignMapper.selectTaskNewPeopleSignById(id);
    }

    /**
     * 查询新人七日签到配置列表
     * 
     * @param taskNewPeopleSign 新人七日签到配置
     * @return 新人七日签到配置
     */
    @Override
    public List<TaskNewPeopleSign> selectTaskNewPeopleSignList(TaskNewPeopleSign taskNewPeopleSign)
    {
        return taskNewPeopleSignMapper.selectTaskNewPeopleSignList(taskNewPeopleSign);
    }

    /**
     * 新增新人七日签到配置
     * 
     * @param taskNewPeopleSign 新人七日签到配置
     * @return 结果
     */
    @Override
    public int insertTaskNewPeopleSign(TaskNewPeopleSign taskNewPeopleSign)
    {
        taskNewPeopleSign.setCreateTime(DateUtils.getNowDate());
        return taskNewPeopleSignMapper.insertTaskNewPeopleSign(taskNewPeopleSign);
    }

    /**
     * 修改新人七日签到配置
     * 
     * @param taskNewPeopleSign 新人七日签到配置
     * @return 结果
     */
    @Override
    public int updateTaskNewPeopleSign(TaskNewPeopleSign taskNewPeopleSign)
    {
        taskNewPeopleSign.setUpdateTime(DateUtils.getNowDate());
        return taskNewPeopleSignMapper.updateTaskNewPeopleSign(taskNewPeopleSign);
    }

    /**
     * 批量删除新人七日签到配置
     * 
     * @param ids 需要删除的新人七日签到配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskNewPeopleSignByIds(Long[] ids)
    {
        return taskNewPeopleSignMapper.deleteTaskNewPeopleSignByIds(ids);
    }

    /**
     * 删除新人七日签到配置信息
     * 
     * @param id 新人七日签到配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskNewPeopleSignById(Long id)
    {
        return taskNewPeopleSignMapper.deleteTaskNewPeopleSignById(id);
    }
}
