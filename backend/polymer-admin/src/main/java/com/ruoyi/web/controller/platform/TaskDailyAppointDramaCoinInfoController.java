package com.ruoyi.web.controller.platform;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.platform.domain.TaskDailyAppointDramaCoinInfo;
import com.ruoyi.platform.service.ITaskDailyAppointDramaCoinInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 看指定剧领金币配置信息Controller
 * 
 * @author ruoyi
 * @date 2024-11-25
 */
@RestController
@RequestMapping("/platform/taskDailyAppointDramaCoinInfo")
public class TaskDailyAppointDramaCoinInfoController extends BaseController
{
    @Autowired
    private ITaskDailyAppointDramaCoinInfoService taskDailyAppointDramaCoinInfoService;

    /**
     * 查询看指定剧领金币配置信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoinInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo)
    {
        startPage();
        List<TaskDailyAppointDramaCoinInfo> list = taskDailyAppointDramaCoinInfoService.selectTaskDailyAppointDramaCoinInfoList(taskDailyAppointDramaCoinInfo);
        return getDataTable(list);
    }

    /**
     * 导出看指定剧领金币配置信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoinInfo:export')")
    @Log(title = "看指定剧领金币配置信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo)
    {
        List<TaskDailyAppointDramaCoinInfo> list = taskDailyAppointDramaCoinInfoService.selectTaskDailyAppointDramaCoinInfoList(taskDailyAppointDramaCoinInfo);
        ExcelUtil<TaskDailyAppointDramaCoinInfo> util = new ExcelUtil<TaskDailyAppointDramaCoinInfo>(TaskDailyAppointDramaCoinInfo.class);
        util.exportExcel(response, list, "看指定剧领金币配置信息数据");
    }

    /**
     * 获取看指定剧领金币配置信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoinInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taskDailyAppointDramaCoinInfoService.selectTaskDailyAppointDramaCoinInfoById(id));
    }

    /**
     * 新增看指定剧领金币配置信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoinInfo:add')")
    @Log(title = "看指定剧领金币配置信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo)
    {
        return toAjax(taskDailyAppointDramaCoinInfoService.insertTaskDailyAppointDramaCoinInfo(taskDailyAppointDramaCoinInfo));
    }

    /**
     * 修改看指定剧领金币配置信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoinInfo:edit')")
    @Log(title = "看指定剧领金币配置信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo)
    {
        if (taskDailyAppointDramaCoinInfo.getSecondInfo()<=0){
            return AjaxResult.error("秒必须大于0");
        }
        if (Objects.isNull(taskDailyAppointDramaCoinInfo.getPrice()) || taskDailyAppointDramaCoinInfo.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            return AjaxResult.error("金额必须大于0");
        }
        return toAjax(taskDailyAppointDramaCoinInfoService.updateTaskDailyAppointDramaCoinInfo(taskDailyAppointDramaCoinInfo));
    }

    /**
     * 删除看指定剧领金币配置信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyAppointDramaCoinInfo:remove')")
    @Log(title = "看指定剧领金币配置信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskDailyAppointDramaCoinInfoService.deleteTaskDailyAppointDramaCoinInfoByIds(ids));
    }
}
