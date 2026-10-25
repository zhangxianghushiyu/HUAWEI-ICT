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
import com.ruoyi.platform.domain.TaskNewPeopleLookDrama;
import com.ruoyi.platform.service.ITaskNewPeopleLookDramaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 新人看剧送现金配置Controller
 * 
 * @author ruoyi
 * @date 2024-11-21
 */
@RestController
@RequestMapping("/platform/taskNewPeopleLookDrama")
public class TaskNewPeopleLookDramaController extends BaseController
{
    @Autowired
    private ITaskNewPeopleLookDramaService taskNewPeopleLookDramaService;

    /**
     * 查询新人看剧送现金配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleLookDrama:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskNewPeopleLookDrama taskNewPeopleLookDrama)
    {
        startPage();
        List<TaskNewPeopleLookDrama> list = taskNewPeopleLookDramaService.selectTaskNewPeopleLookDramaList(taskNewPeopleLookDrama);
        return getDataTable(list);
    }

    /**
     * 导出新人看剧送现金配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleLookDrama:export')")
    @Log(title = "新人看剧送现金配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskNewPeopleLookDrama taskNewPeopleLookDrama)
    {
        List<TaskNewPeopleLookDrama> list = taskNewPeopleLookDramaService.selectTaskNewPeopleLookDramaList(taskNewPeopleLookDrama);
        ExcelUtil<TaskNewPeopleLookDrama> util = new ExcelUtil<TaskNewPeopleLookDrama>(TaskNewPeopleLookDrama.class);
        util.exportExcel(response, list, "新人看剧送现金配置数据");
    }

    /**
     * 获取新人看剧送现金配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleLookDrama:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taskNewPeopleLookDramaService.selectTaskNewPeopleLookDramaById(id));
    }

    /**
     * 新增新人看剧送现金配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleLookDrama:add')")
    @Log(title = "新人看剧送现金配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskNewPeopleLookDrama taskNewPeopleLookDrama)
    {
        return toAjax(taskNewPeopleLookDramaService.insertTaskNewPeopleLookDrama(taskNewPeopleLookDrama));
    }

    /**
     * 修改新人看剧送现金配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleLookDrama:edit')")
    @Log(title = "新人看剧送现金配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskNewPeopleLookDrama taskNewPeopleLookDrama)
    {
        if (Objects.isNull(taskNewPeopleLookDrama.getPrice()) || taskNewPeopleLookDrama.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            return AjaxResult.error("现金必须大于0");
        }
        return toAjax(taskNewPeopleLookDramaService.updateTaskNewPeopleLookDrama(taskNewPeopleLookDrama));
    }

    /**
     * 删除新人看剧送现金配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taskNewPeopleLookDrama:remove')")
    @Log(title = "新人看剧送现金配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskNewPeopleLookDramaService.deleteTaskNewPeopleLookDramaByIds(ids));
    }
}
