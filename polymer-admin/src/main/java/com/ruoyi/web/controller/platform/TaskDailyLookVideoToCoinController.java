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
import com.ruoyi.platform.domain.TaskDailyLookVideoToCoin;
import com.ruoyi.platform.service.ITaskDailyLookVideoToCoinService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 看视频领金币配置Controller
 *
 * @author ruoyi
 * @date 2024-11-22
 */
@RestController
@RequestMapping("/platform/taskDailyLookVideoToCoin")
public class TaskDailyLookVideoToCoinController extends BaseController {
    @Autowired
    private ITaskDailyLookVideoToCoinService taskDailyLookVideoToCoinService;

    /**
     * 查询看视频领金币配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookVideoToCoin:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskDailyLookVideoToCoin taskDailyLookVideoToCoin) {
        startPage();
        List<TaskDailyLookVideoToCoin> list = taskDailyLookVideoToCoinService.selectTaskDailyLookVideoToCoinList(taskDailyLookVideoToCoin);
        return getDataTable(list);
    }

    /**
     * 导出看视频领金币配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookVideoToCoin:export')")
    @Log(title = "看视频领金币配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskDailyLookVideoToCoin taskDailyLookVideoToCoin) {
        List<TaskDailyLookVideoToCoin> list = taskDailyLookVideoToCoinService.selectTaskDailyLookVideoToCoinList(taskDailyLookVideoToCoin);
        ExcelUtil<TaskDailyLookVideoToCoin> util = new ExcelUtil<TaskDailyLookVideoToCoin>(TaskDailyLookVideoToCoin.class);
        util.exportExcel(response, list, "看视频领金币配置数据");
    }

    /**
     * 获取看视频领金币配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookVideoToCoin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(taskDailyLookVideoToCoinService.selectTaskDailyLookVideoToCoinById(id));
    }

    /**
     * 新增看视频领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookVideoToCoin:add')")
    @Log(title = "看视频领金币配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskDailyLookVideoToCoin taskDailyLookVideoToCoin) {
        return toAjax(taskDailyLookVideoToCoinService.insertTaskDailyLookVideoToCoin(taskDailyLookVideoToCoin));
    }

    /**
     * 修改看视频领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookVideoToCoin:edit')")
    @Log(title = "看视频领金币配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskDailyLookVideoToCoin taskDailyLookVideoToCoin) {
        if (taskDailyLookVideoToCoin.getCount()<=0){
            return AjaxResult.error("次数必须大于0");
        }
        if (Objects.isNull(taskDailyLookVideoToCoin.getPrice()) || taskDailyLookVideoToCoin.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            return AjaxResult.error("金币必须大于0");
        }
        return toAjax(taskDailyLookVideoToCoinService.updateTaskDailyLookVideoToCoin(taskDailyLookVideoToCoin));
    }

    /**
     * 删除看视频领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookVideoToCoin:remove')")
    @Log(title = "看视频领金币配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(taskDailyLookVideoToCoinService.deleteTaskDailyLookVideoToCoinByIds(ids));
    }
}
