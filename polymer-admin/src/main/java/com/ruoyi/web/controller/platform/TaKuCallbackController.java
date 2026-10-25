package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.TaKuCallback;
import com.ruoyi.platform.service.ITaKuCallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * taKu广告回调Controller
 * 
 * @author ruoyi
 * @date 2025-02-27
 */
@RestController
@RequestMapping("/platform/taKuCallback")
public class TaKuCallbackController extends BaseController
{
    @Autowired
    private ITaKuCallbackService taKuCallbackService;

    /**
     * 查询taKu广告回调列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuCallback:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaKuCallback taKuCallback)
    {
        startPage();
        List<TaKuCallback> list = taKuCallbackService.selectTaKuCallbackList(taKuCallback);
        return getDataTable(list);
    }

    /**
     * 导出taKu广告回调列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuCallback:export')")
    @Log(title = "taKu广告回调", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaKuCallback taKuCallback)
    {
        List<TaKuCallback> list = taKuCallbackService.selectTaKuCallbackList(taKuCallback);
        ExcelUtil<TaKuCallback> util = new ExcelUtil<TaKuCallback>(TaKuCallback.class);
        util.exportExcel(response, list, "taKu广告回调数据");
    }

    /**
     * 获取taKu广告回调详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuCallback:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taKuCallbackService.selectTaKuCallbackById(id));
    }

    /**
     * 新增taKu广告回调
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuCallback:add')")
    @Log(title = "taKu广告回调", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaKuCallback taKuCallback)
    {
        return toAjax(taKuCallbackService.insertTaKuCallback(taKuCallback));
    }

    /**
     * 修改taKu广告回调
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuCallback:edit')")
    @Log(title = "taKu广告回调", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaKuCallback taKuCallback)
    {
        return toAjax(taKuCallbackService.updateTaKuCallback(taKuCallback));
    }

    /**
     * 删除taKu广告回调
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuCallback:remove')")
    @Log(title = "taKu广告回调", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taKuCallbackService.deleteTaKuCallbackByIds(ids));
    }
}
