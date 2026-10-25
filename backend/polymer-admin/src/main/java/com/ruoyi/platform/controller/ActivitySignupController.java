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
import com.ruoyi.platform.domain.ActivitySignup;
import com.ruoyi.platform.service.IActivitySignupService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报名记录Controller
 * 
 * @author lhr
 * @date 2026-06-08
 */
@RestController
@RequestMapping("/platform/signup")
public class ActivitySignupController extends BaseController
{
    @Autowired
    private IActivitySignupService activitySignupService;

    /**
     * 查询报名记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:signup:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActivitySignup activitySignup)
    {
        startPage();
        List<ActivitySignup> list = activitySignupService.selectActivitySignupList(activitySignup);
        return getDataTable(list);
    }

    /**
     * 导出报名记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:signup:export')")
    @Log(title = "报名记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ActivitySignup activitySignup)
    {
        List<ActivitySignup> list = activitySignupService.selectActivitySignupList(activitySignup);
        ExcelUtil<ActivitySignup> util = new ExcelUtil<ActivitySignup>(ActivitySignup.class);
        util.exportExcel(response, list, "报名记录数据");
    }

    /**
     * 获取报名记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:signup:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(activitySignupService.selectActivitySignupById(id));
    }

    /**
     * 新增报名记录
     */
    @PreAuthorize("@ss.hasPermi('platform:signup:add')")
    @Log(title = "报名记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActivitySignup activitySignup)
    {
        return toAjax(activitySignupService.insertActivitySignup(activitySignup));
    }

    /**
     * 修改报名记录
     */
    @PreAuthorize("@ss.hasPermi('platform:signup:edit')")
    @Log(title = "报名记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActivitySignup activitySignup)
    {
        return toAjax(activitySignupService.updateActivitySignup(activitySignup));
    }

    /**
     * 删除报名记录
     */
    @PreAuthorize("@ss.hasPermi('platform:signup:remove')")
    @Log(title = "报名记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(activitySignupService.deleteActivitySignupByIds(ids));
    }
}
