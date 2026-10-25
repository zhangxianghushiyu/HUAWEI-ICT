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
import com.ruoyi.platform.domain.TaskNewPeopleSign;
import com.ruoyi.platform.service.ITaskNewPeopleSignService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新人七日签到配置Controller
 * 
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/platform/taskNewPeopleSign")
public class TaskNewPeopleSignController extends BaseController
{
    @Autowired
    private ITaskNewPeopleSignService taskNewPeopleSignService;

    /**
     * 查询新人七日签到配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleSign:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskNewPeopleSign taskNewPeopleSign)
    {
        startPage();
        List<TaskNewPeopleSign> list = taskNewPeopleSignService.selectTaskNewPeopleSignList(taskNewPeopleSign);
        return getDataTable(list);
    }

    /**
     * 导出新人七日签到配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleSign:export')")
    @Log(title = "新人七日签到配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskNewPeopleSign taskNewPeopleSign)
    {
        List<TaskNewPeopleSign> list = taskNewPeopleSignService.selectTaskNewPeopleSignList(taskNewPeopleSign);
        ExcelUtil<TaskNewPeopleSign> util = new ExcelUtil<TaskNewPeopleSign>(TaskNewPeopleSign.class);
        util.exportExcel(response, list, "新人七日签到配置数据");
    }

    /**
     * 获取新人七日签到配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleSign:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taskNewPeopleSignService.selectTaskNewPeopleSignById(id));
    }

    /**
     * 新增新人七日签到配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleSign:add')")
    @Log(title = "新人七日签到配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskNewPeopleSign taskNewPeopleSign)
    {
        return toAjax(taskNewPeopleSignService.insertTaskNewPeopleSign(taskNewPeopleSign));
    }

    /**
     * 修改新人七日签到配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleSign:edit')")
    @Log(title = "新人七日签到配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskNewPeopleSign taskNewPeopleSign)
    {
        if (Objects.isNull(taskNewPeopleSign.getPrice()) || taskNewPeopleSign.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            return AjaxResult.error("金币必须大于0");
        }
        return toAjax(taskNewPeopleSignService.updateTaskNewPeopleSign(taskNewPeopleSign));
    }

    /**
     * 删除新人七日签到配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleSign:remove')")
    @Log(title = "新人七日签到配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskNewPeopleSignService.deleteTaskNewPeopleSignByIds(ids));
    }
}
