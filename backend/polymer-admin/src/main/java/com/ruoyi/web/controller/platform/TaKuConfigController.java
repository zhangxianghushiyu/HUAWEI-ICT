package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.TaKuConfig;
import com.ruoyi.platform.service.ITaKuConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * taKu广告配置Controller
 * 
 * @author ruoyi
 * @date 2025-02-27
 */
@RestController
@RequestMapping("/platform/taKuConfig")
public class TaKuConfigController extends BaseController
{
    @Autowired
    private ITaKuConfigService taKuConfigService;

    /**
     * 查询taKu广告配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaKuConfig taKuConfig)
    {
        startPage();
        List<TaKuConfig> list = taKuConfigService.selectTaKuConfigList(taKuConfig);
        return getDataTable(list);
    }

    /**
     * 导出taKu广告配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuConfig:export')")
    @Log(title = "taKu广告配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaKuConfig taKuConfig)
    {
        List<TaKuConfig> list = taKuConfigService.selectTaKuConfigList(taKuConfig);
        ExcelUtil<TaKuConfig> util = new ExcelUtil<TaKuConfig>(TaKuConfig.class);
        util.exportExcel(response, list, "taKu广告配置数据");
    }

    /**
     * 获取taKu广告配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(taKuConfigService.selectTaKuConfigById(id));
    }

    /**
     * 新增taKu广告配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuConfig:add')")
    @Log(title = "taKu广告配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaKuConfig taKuConfig)
    {
        return toAjax(taKuConfigService.insertTaKuConfig(taKuConfig));
    }

    /**
     * 修改taKu广告配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuConfig:edit')")
    @Log(title = "taKu广告配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaKuConfig taKuConfig)
    {
        return toAjax(taKuConfigService.updateTaKuConfig(taKuConfig));
    }

    /**
     * 删除taKu广告配置
     */
    @PreAuthorize("@ss.hasPermi('platform:taKuConfig:remove')")
    @Log(title = "taKu广告配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taKuConfigService.deleteTaKuConfigByIds(ids));
    }
}
