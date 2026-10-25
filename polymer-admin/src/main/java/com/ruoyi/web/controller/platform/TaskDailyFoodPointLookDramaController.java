package com.ruoyi.web.controller.platform;

import java.util.List;
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
import com.ruoyi.platform.domain.TaskDailyFoodPointLookDrama;
import com.ruoyi.platform.service.ITaskDailyFoodPointLookDramaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 饭点看剧领金币配置Controller
 * 
 * @author ruoyi
 * @date 2024-11-22
 */
@RestController
@RequestMapping("/platform/taskDailyFoodPointLookDrama")
public class TaskDailyFoodPointLookDramaController extends BaseController
{
    @Autowired
    private ITaskDailyFoodPointLookDramaService taskDailyFoodPointLookDramaService;

    /**
     * 查询饭点看剧领金币配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyFoodPointLookDrama:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama)
    {
        startPage();
        List<TaskDailyFoodPointLookDrama> list = taskDailyFoodPointLookDramaService.selectTaskDailyFoodPointLookDramaList(taskDailyFoodPointLookDrama);
        return getDataTable(list);
    }

    /**
     * 导出饭点看剧领金币配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyFoodPointLookDrama:export')")
    @Log(title = "饭点看剧领金币配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama)
    {
        List<TaskDailyFoodPointLookDrama> list = taskDailyFoodPointLookDramaService.selectTaskDailyFoodPointLookDramaList(taskDailyFoodPointLookDrama);
        ExcelUtil<TaskDailyFoodPointLookDrama> util = new ExcelUtil<TaskDailyFoodPointLookDrama>(TaskDailyFoodPointLookDrama.class);
        util.exportExcel(response, list, "饭点看剧领金币配置数据");
    }

    /**
     * 获取饭点看剧领金币配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyFoodPointLookDrama:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taskDailyFoodPointLookDramaService.selectTaskDailyFoodPointLookDramaById(id));
    }

    /**
     * 新增饭点看剧领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyFoodPointLookDrama:add')")
    @Log(title = "饭点看剧领金币配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama)
    {
        return toAjax(taskDailyFoodPointLookDramaService.insertTaskDailyFoodPointLookDrama(taskDailyFoodPointLookDrama));
    }

    /**
     * 修改饭点看剧领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyFoodPointLookDrama:edit')")
    @Log(title = "饭点看剧领金币配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama)
    {
        return toAjax(taskDailyFoodPointLookDramaService.updateTaskDailyFoodPointLookDrama(taskDailyFoodPointLookDrama));
    }

    /**
     * 删除饭点看剧领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyFoodPointLookDrama:remove')")
    @Log(title = "饭点看剧领金币配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskDailyFoodPointLookDramaService.deleteTaskDailyFoodPointLookDramaByIds(ids));
    }
}
