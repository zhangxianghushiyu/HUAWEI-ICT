package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.TaskNewPeopleLookDramaMapper;
import com.ruoyi.platform.domain.TaskNewPeopleLookDrama;
import com.ruoyi.platform.service.ITaskNewPeopleLookDramaService;

/**
 * 新人看剧送现金配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-21
 */
@Service
public class TaskNewPeopleLookDramaServiceImpl implements ITaskNewPeopleLookDramaService 
{
    @Autowired
    private TaskNewPeopleLookDramaMapper taskNewPeopleLookDramaMapper;

    /**
     * 查询新人看剧送现金配置
     * 
     * @param id 新人看剧送现金配置主键
     * @return 新人看剧送现金配置
     */
    @Override
    public TaskNewPeopleLookDrama selectTaskNewPeopleLookDramaById(Long id)
    {
        return taskNewPeopleLookDramaMapper.selectTaskNewPeopleLookDramaById(id);
    }

    /**
     * 查询新人看剧送现金配置列表
     * 
     * @param taskNewPeopleLookDrama 新人看剧送现金配置
     * @return 新人看剧送现金配置
     */
    @Override
    public List<TaskNewPeopleLookDrama> selectTaskNewPeopleLookDramaList(TaskNewPeopleLookDrama taskNewPeopleLookDrama)
    {
        return taskNewPeopleLookDramaMapper.selectTaskNewPeopleLookDramaList(taskNewPeopleLookDrama);
    }

    /**
     * 新增新人看剧送现金配置
     * 
     * @param taskNewPeopleLookDrama 新人看剧送现金配置
     * @return 结果
     */
    @Override
    public int insertTaskNewPeopleLookDrama(TaskNewPeopleLookDrama taskNewPeopleLookDrama)
    {
        taskNewPeopleLookDrama.setCreateTime(DateUtils.getNowDate());
        return taskNewPeopleLookDramaMapper.insertTaskNewPeopleLookDrama(taskNewPeopleLookDrama);
    }

    /**
     * 修改新人看剧送现金配置
     * 
     * @param taskNewPeopleLookDrama 新人看剧送现金配置
     * @return 结果
     */
    @Override
    public int updateTaskNewPeopleLookDrama(TaskNewPeopleLookDrama taskNewPeopleLookDrama)
    {
        taskNewPeopleLookDrama.setUpdateTime(DateUtils.getNowDate());
        return taskNewPeopleLookDramaMapper.updateTaskNewPeopleLookDrama(taskNewPeopleLookDrama);
    }

    /**
     * 批量删除新人看剧送现金配置
     * 
     * @param ids 需要删除的新人看剧送现金配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskNewPeopleLookDramaByIds(Long[] ids)
    {
        return taskNewPeopleLookDramaMapper.deleteTaskNewPeopleLookDramaByIds(ids);
    }

    /**
     * 删除新人看剧送现金配置信息
     * 
     * @param id 新人看剧送现金配置主键
     * @return 结果
     */
    @Override
    public int deleteTaskNewPeopleLookDramaById(Long id)
    {
        return taskNewPeopleLookDramaMapper.deleteTaskNewPeopleLookDramaById(id);
    }
}
