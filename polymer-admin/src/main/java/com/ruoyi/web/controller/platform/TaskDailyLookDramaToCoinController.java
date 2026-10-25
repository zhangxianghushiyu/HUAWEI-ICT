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
import com.ruoyi.platform.domain.TaskDailyLookDramaToCoin;
import com.ruoyi.platform.service.ITaskDailyLookDramaToCoinService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 看剧领金币配置Controller
 *
 * @author ruoyi
 * @date 2024-11-22
 */
@RestController
@RequestMapping("/platform/taskDailyLookDramaToCoin")
public class TaskDailyLookDramaToCoinController extends BaseController {
    @Autowired
    private ITaskDailyLookDramaToCoinService taskDailyLookDramaToCoinService;

    /**
     * 查询看剧领金币配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookDramaToCoin:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskDailyLookDramaToCoin taskDailyLookDramaToCoin) {
        startPage();
        List<TaskDailyLookDramaToCoin> list = taskDailyLookDramaToCoinService.selectTaskDailyLookDramaToCoinList(taskDailyLookDramaToCoin);
        return getDataTable(list);
    }

    /**
     * 导出看剧领金币配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookDramaToCoin:export')")
    @Log(title = "看剧领金币配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskDailyLookDramaToCoin taskDailyLookDramaToCoin) {
        List<TaskDailyLookDramaToCoin> list = taskDailyLookDramaToCoinService.selectTaskDailyLookDramaToCoinList(taskDailyLookDramaToCoin);
        ExcelUtil<TaskDailyLookDramaToCoin> util = new ExcelUtil<TaskDailyLookDramaToCoin>(TaskDailyLookDramaToCoin.class);
        util.exportExcel(response, list, "看剧领金币配置数据");
    }

    /**
     * 获取看剧领金币配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookDramaToCoin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(taskDailyLookDramaToCoinService.selectTaskDailyLookDramaToCoinById(id));
    }

    /**
     * 新增看剧领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookDramaToCoin:add')")
    @Log(title = "看剧领金币配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskDailyLookDramaToCoin taskDailyLookDramaToCoin) {
        return toAjax(taskDailyLookDramaToCoinService.insertTaskDailyLookDramaToCoin(taskDailyLookDramaToCoin));
    }

    /**
     * 修改看剧领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookDramaToCoin:edit')")
    @Log(title = "看剧领金币配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskDailyLookDramaToCoin taskDailyLookDramaToCoin) {
        //查询秒数是不是整分数
        if (taskDailyLookDramaToCoin.getSecondInfo() % 60 != 0) {
            return AjaxResult.error("请输入整分数");
        }
        if (Objects.isNull(taskDailyLookDramaToCoin.getPrice()) || taskDailyLookDramaToCoin.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            return AjaxResult.error("金币必须大于0");
        }
        return toAjax(taskDailyLookDramaToCoinService.updateTaskDailyLookDramaToCoin(taskDailyLookDramaToCoin));
    }

    /**
     * 删除看剧领金币配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskDailyLookDramaToCoin:remove')")
    @Log(title = "看剧领金币配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(taskDailyLookDramaToCoinService.deleteTaskDailyLookDramaToCoinByIds(ids));
    }
}
