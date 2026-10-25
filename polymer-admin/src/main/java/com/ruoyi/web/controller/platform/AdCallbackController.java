package com.ruoyi.web.controller.platform;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.dto.AdCallbackDTO;
import com.ruoyi.platform.vo.AdCallbackVO;
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
import com.ruoyi.platform.domain.AdCallback;
import com.ruoyi.platform.service.IAdCallbackService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 广告回调Controller
 * 
 * @author ruoyi
 * @date 2025-01-08
 */
@RestController
@RequestMapping("/platform/adCallback")
public class AdCallbackController extends BaseController
{
    @Autowired
    private IAdCallbackService adCallbackService;

    /**
     * 查询广告回调列表
     */
    @PreAuthorize("@ss.hasPermi('platform:adCallback:list')")
    @GetMapping("/list")
    public TableDataInfo list(AdCallbackDTO adCallback)
    {
        startPage();
        List<AdCallbackVO> list = adCallbackService.selectAdCallbackListInfo(adCallback);
        return getDataTable(list);
    }

    /**
     * 导出广告回调列表
     */
    @PreAuthorize("@ss.hasPermi('platform:adCallback:export')")
    @Log(title = "广告回调", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AdCallback adCallback)
    {
        List<AdCallback> list = adCallbackService.selectAdCallbackList(adCallback);
        ExcelUtil<AdCallback> util = new ExcelUtil<AdCallback>(AdCallback.class);
        util.exportExcel(response, list, "广告回调数据");
    }

    /**
     * 获取广告回调详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:adCallback:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(adCallbackService.selectAdCallbackById(id));
    }

    /**
     * 新增广告回调
     */
    @PreAuthorize("@ss.hasPermi('platform:adCallback:add')")
    @Log(title = "广告回调", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AdCallback adCallback)
    {
        return toAjax(adCallbackService.insertAdCallback(adCallback));
    }

    /**
     * 修改广告回调
     */
    @PreAuthorize("@ss.hasPermi('platform:adCallback:edit')")
    @Log(title = "广告回调", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AdCallback adCallback)
    {
        return toAjax(adCallbackService.updateAdCallback(adCallback));
    }

    /**
     * 删除广告回调
     */
    @PreAuthorize("@ss.hasPermi('platform:adCallback:remove')")
    @Log(title = "广告回调", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(adCallbackService.deleteAdCallbackByIds(ids));
    }
}
