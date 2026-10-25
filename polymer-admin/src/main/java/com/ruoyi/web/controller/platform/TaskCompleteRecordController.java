package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.TaskCompleteRecord;
import com.ruoyi.platform.service.ITaskCompleteRecordService;
import com.ruoyi.platform.vo.TaskCompleteRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 任务完成记录Controller
 *
 * @author ruoyi
 * @date 2024-11-19
 */
@RestController
@RequestMapping("/platform/taskCompleteRecord")
public class TaskCompleteRecordController extends BaseController
{
    @Autowired
    private ITaskCompleteRecordService taskCompleteRecordService;

    /**
     * 查询任务完成记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskCompleteRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskCompleteRecordVO taskCompleteRecordVO)
    {
        startPage();
        List<TaskCompleteRecordVO> list = taskCompleteRecordService.selectTaskCompleteRecordDetailList(taskCompleteRecordVO);
        return getDataTable(list);
    }

    /**
     * 导出任务完成记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskCompleteRecord:export')")
    @Log(title = "任务完成记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskCompleteRecord taskCompleteRecord)
    {
        List<TaskCompleteRecord> list = taskCompleteRecordService.selectTaskCompleteRecordList(taskCompleteRecord);
        ExcelUtil<TaskCompleteRecord> util = new ExcelUtil<TaskCompleteRecord>(TaskCompleteRecord.class);
        util.exportExcel(response, list, "任务完成记录数据");
    }

    /**
     * 获取任务完成记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskCompleteRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taskCompleteRecordService.selectTaskCompleteRecordById(id));
    }

    /**
     * 新增任务完成记录
     */
    @PreAuthorize("@ss.hasPermi('platform:taskCompleteRecord:add')")
    @Log(title = "任务完成记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskCompleteRecord taskCompleteRecord)
    {
        return toAjax(taskCompleteRecordService.insertTaskCompleteRecord(taskCompleteRecord));
    }

    /**
     * 修改任务完成记录
     */
    @PreAuthorize("@ss.hasPermi('platform:taskCompleteRecord:edit')")
    @Log(title = "任务完成记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskCompleteRecord taskCompleteRecord)
    {
        return toAjax(taskCompleteRecordService.updateTaskCompleteRecord(taskCompleteRecord));
    }

    /**
     * 删除任务完成记录
     */
    @PreAuthorize("@ss.hasPermi('platform:taskCompleteRecord:remove')")
    @Log(title = "任务完成记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskCompleteRecordService.deleteTaskCompleteRecordByIds(ids));
    }
}
