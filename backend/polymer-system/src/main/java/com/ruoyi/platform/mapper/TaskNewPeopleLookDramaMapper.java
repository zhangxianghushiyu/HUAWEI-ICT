package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.TaskNewPeopleLookDrama;

/**
 * 新人看剧送现金配置Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-21
 */
public interface TaskNewPeopleLookDramaMapper 
{
    /**
     * 查询新人看剧送现金配置
     * 
     * @param id 新人看剧送现金配置主键
     * @return 新人看剧送现金配置
     */
    public TaskNewPeopleLookDrama selectTaskNewPeopleLookDramaById(Long id);

    /**
     * 查询新人看剧送现金配置列表
     * 
     * @param taskNewPeopleLookDrama 新人看剧送现金配置
     * @return 新人看剧送现金配置集合
     */
    public List<TaskNewPeopleLookDrama> selectTaskNewPeopleLookDramaList(TaskNewPeopleLookDrama taskNewPeopleLookDrama);

    /**
     * 新增新人看剧送现金配置
     * 
     * @param taskNewPeopleLookDrama 新人看剧送现金配置
     * @return 结果
     */
    public int insertTaskNewPeopleLookDrama(TaskNewPeopleLookDrama taskNewPeopleLookDrama);

    /**
     * 修改新人看剧送现金配置
     * 
     * @param taskNewPeopleLookDrama 新人看剧送现金配置
     * @return 结果
     */
    public int updateTaskNewPeopleLookDrama(TaskNewPeopleLookDrama taskNewPeopleLookDrama);

    /**
     * 删除新人看剧送现金配置
     * 
     * @param id 新人看剧送现金配置主键
     * @return 结果
     */
    public int deleteTaskNewPeopleLookDramaById(Long id);

    /**
     * 批量删除新人看剧送现金配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaskNewPeopleLookDramaByIds(Long[] ids);
}
