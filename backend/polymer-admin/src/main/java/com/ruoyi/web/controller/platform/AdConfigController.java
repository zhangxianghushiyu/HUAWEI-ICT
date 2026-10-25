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
import com.ruoyi.platform.domain.AdConfig;
import com.ruoyi.platform.service.IAdConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 广告配置Controller
 * 
 * @author ruoyi
 * @date 2025-01-07
 */
@RestController
@RequestMapping("/platform/adConfig")
public class AdConfigController extends BaseController
{
    @Autowired
    private IAdConfigService adConfigService;

    /**
     * 查询广告配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:adConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(AdConfig adConfig)
    {
        startPage();
        List<AdConfig> list = adConfigService.selectAdConfigList(adConfig);
        return getDataTable(list);
    }

    /**
     * 导出广告配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:adConfig:export')")
    @Log(title = "广告配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AdConfig adConfig)
    {
        List<AdConfig> list = adConfigService.selectAdConfigList(adConfig);
        ExcelUtil<AdConfig> util = new ExcelUtil<AdConfig>(AdConfig.class);
        util.exportExcel(response, list, "广告配置数据");
    }

    /**
     * 获取广告配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:adConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(adConfigService.selectAdConfigById(id));
    }

    /**
     * 新增广告配置
     */
    @PreAuthorize("@ss.hasPermi('platform:adConfig:add')")
    @Log(title = "广告配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AdConfig adConfig)
    {
        return toAjax(adConfigService.insertAdConfig(adConfig));
    }

    /**
     * 修改广告配置
     */
    @PreAuthorize("@ss.hasPermi('platform:adConfig:edit')")
    @Log(title = "广告配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AdConfig adConfig)
    {
        return toAjax(adConfigService.updateAdConfig(adConfig));
    }

    /**
     * 删除广告配置
     */
    @PreAuthorize("@ss.hasPermi('platform:adConfig:remove')")
    @Log(title = "广告配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(adConfigService.deleteAdConfigByIds(ids));
    }
}
