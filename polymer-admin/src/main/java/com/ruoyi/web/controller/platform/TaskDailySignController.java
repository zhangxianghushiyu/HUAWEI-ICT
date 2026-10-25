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
import com.ruoyi.platform.domain.TaskDailySign;
import com.ruoyi.platform.service.ITaskDailySignService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 每日七日签到配置Controller
 * 
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/platform/taskDailySign")
public class TaskDailySignController extends BaseController
{
    @Autowired
    private ITaskDailySignService taskDailySignService;

    /**
     * 查询每日七日签到配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailySign:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskDailySign taskDailySign)
    {
        startPage();
        List<TaskDailySign> list = taskDailySignService.selectTaskDailySignList(taskDailySign);
        return getDataTable(list);
    }

    /**
     * 导出每日七日签到配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailySign:export')")
    @Log(title = "每日七日签到配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskDailySign taskDailySign)
    {
        List<TaskDailySign> list = taskDailySignService.selectTaskDailySignList(taskDailySign);
        ExcelUtil<TaskDailySign> util = new ExcelUtil<TaskDailySign>(TaskDailySign.class);
        util.exportExcel(response, list, "每日七日签到配置数据");
    }

    /**
     * 获取每日七日签到配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailySign:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taskDailySignService.selectTaskDailySignById(id));
    }

    /**
     * 新增每日七日签到配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailySign:add')")
    @Log(title = "每日七日签到配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskDailySign taskDailySign)
    {
        return toAjax(taskDailySignService.insertTaskDailySign(taskDailySign));
    }

    /**
     * 修改每日七日签到配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailySign:edit')")
    @Log(title = "每日七日签到配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskDailySign taskDailySign)
    {
        if (Objects.isNull(taskDailySign.getPrice()) || taskDailySign.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            return AjaxResult.error("金币必须大于0");
        }
        return toAjax(taskDailySignService.updateTaskDailySign(taskDailySign));
    }

    /**
     * 删除每日七日签到配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailySign:remove')")
    @Log(title = "每日七日签到配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskDailySignService.deleteTaskDailySignByIds(ids));
    }
}
