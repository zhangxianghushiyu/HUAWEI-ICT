package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.TaskNewPeopleSign;

/**
 * 新人七日签到配置Service接口
 * 
 * @author ruoyi
 * @date 2024-11-21
 */
public interface ITaskNewPeopleSignService 
{
    /**
     * 查询新人七日签到配置
     * 
     * @param id 新人七日签到配置主键
     * @return 新人七日签到配置
     */
    public TaskNewPeopleSign selectTaskNewPeopleSignById(Long id);

    /**
     * 查询新人七日签到配置列表
     * 
     * @param taskNewPeopleSign 新人七日签到配置
     * @return 新人七日签到配置集合
     */
    public List<TaskNewPeopleSign> selectTaskNewPeopleSignList(TaskNewPeopleSign taskNewPeopleSign);

    /**
     * 新增新人七日签到配置
     * 
     * @param taskNewPeopleSign 新人七日签到配置
     * @return 结果
     */
    public int insertTaskNewPeopleSign(TaskNewPeopleSign taskNewPeopleSign);

    /**
     * 修改新人七日签到配置
     * 
     * @param taskNewPeopleSign 新人七日签到配置
     * @return 结果
     */
    public int updateTaskNewPeopleSign(TaskNewPeopleSign taskNewPeopleSign);

    /**
     * 批量删除新人七日签到配置
     * 
     * @param ids 需要删除的新人七日签到配置主键集合
     * @return 结果
     */
    public int deleteTaskNewPeopleSignByIds(Long[] ids);

    /**
     * 删除新人七日签到配置信息
     * 
     * @param id 新人七日签到配置主键
     * @return 结果
     */
    public int deleteTaskNewPeopleSignById(Long id);
}
