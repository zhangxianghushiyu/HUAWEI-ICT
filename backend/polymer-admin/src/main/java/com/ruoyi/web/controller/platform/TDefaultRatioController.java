package com.ruoyi.web.controller.platform;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.domain.TDefaultRatio;
import com.ruoyi.platform.service.ITDefaultRatioService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 统一默认比例Controller
 *
 * @author ruoyi
 * @date 2025-07-03
 */
@RestController
@RequestMapping("/platform/ratio")
public class TDefaultRatioController extends BaseController {
    @Autowired
    private ITDefaultRatioService tDefaultRatioService;

    /**
     * 查询统一默认比例列表
     */
    @PreAuthorize("@ss.hasPermi('platform:ratio:list')")
    @GetMapping("/list")
    public TableDataInfo list(TDefaultRatio tDefaultRatio) {
        startPage();
        List<TDefaultRatio> list = tDefaultRatioService.selectTDefaultRatioList(tDefaultRatio);
        return getDataTable(list);
    }

    /**
     * 导出统一默认比例列表
     */
    @PreAuthorize("@ss.hasPermi('platform:ratio:export')")
    @Log(title = "统一默认比例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TDefaultRatio tDefaultRatio) {
        List<TDefaultRatio> list = tDefaultRatioService.selectTDefaultRatioList(tDefaultRatio);
        ExcelUtil<TDefaultRatio> util = new ExcelUtil<TDefaultRatio>(TDefaultRatio.class);
        util.exportExcel(response, list, "统一默认比例数据");
    }

    /**
     * 获取统一默认比例详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:ratio:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tDefaultRatioService.selectTDefaultRatioById(id));
    }

    /**
     * 新增统一默认比例
     */
    @PreAuthorize("@ss.hasPermi('platform:ratio:add')")
    @Log(title = "统一默认比例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TDefaultRatio tDefaultRatio) {
        return toAjax(tDefaultRatioService.insertTDefaultRatio(tDefaultRatio));
    }

    /**
     * 修改统一默认比例
     */
    @PreAuthorize("@ss.hasPermi('platform:ratio:edit')")
    @Log(title = "统一默认比例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TDefaultRatio tDefaultRatio) {
        return toAjax(tDefaultRatioService.updateTDefaultRatio(tDefaultRatio));
    }

    /**
     * 删除统一默认比例
     */
    @PreAuthorize("@ss.hasPermi('platform:ratio:remove')")
    @Log(title = "统一默认比例", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tDefaultRatioService.deleteTDefaultRatioByIds(ids));
    }
}
