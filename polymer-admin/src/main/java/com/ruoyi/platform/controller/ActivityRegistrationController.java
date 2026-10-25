package com.ruoyi.platform.controller;

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
import com.ruoyi.platform.domain.ActivityRegistration;
import com.ruoyi.platform.service.IActivityRegistrationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 活动Controller
 * 
 * @author lhr
 * @date 2026-06-08
 */
@RestController
@RequestMapping("/platform/registration")
public class ActivityRegistrationController extends BaseController
{
    @Autowired
    private IActivityRegistrationService activityRegistrationService;

    /**
     * 查询活动列表
     */
    @PreAuthorize("@ss.hasPermi('platform:registration:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivityRegistration activityRegistration)
    {
        startPage();
        List<ActivityRegistration> list = activityRegistrationService.selectActivityRegistrationList(activityRegistration);
        return getDataTable(list);
    }

    /**
     * 导出活动列表
     */
    @PreAuthorize("@ss.hasPermi('platform:registration:export')")
    @Log(title = "活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivityRegistration activityRegistration)
    {
        List<ActivityRegistration> list = activityRegistrationService.selectActivityRegistrationList(activityRegistration);
        ExcelUtil<ActivityRegistration> util = new ExcelUtil<ActivityRegistration>(ActivityRegistration.class);
        util.exportExcel(response, list, "活动数据");
    }

    /**
     * 获取活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:registration:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(activityRegistrationService.selectActivityRegistrationById(id));
    }

    /**
     * 新增活动
     */
    @PreAuthorize("@ss.hasPermi('platform:registration:add')")
    @Log(title = "活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivityRegistration activityRegistration)
    {
        return toAjax(activityRegistrationService.insertActivityRegistration(activityRegistration));
    }

    /**
     * 修改活动
     */
    @PreAuthorize("@ss.hasPermi('platform:registration:edit')")
    @Log(title = "活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivityRegistration activityRegistration)
    {
        return toAjax(activityRegistrationService.updateActivityRegistration(activityRegistration));
    }

    /**
     * 删除活动
     */
    @PreAuthorize("@ss.hasPermi('platform:registration:remove')")
    @Log(title = "活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(activityRegistrationService.deleteActivityRegistrationByIds(ids));
    }
}
