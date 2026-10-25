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
import com.ruoyi.platform.domain.UserDeactivate;
import com.ruoyi.platform.service.IUserDeactivateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户停用记录Controller
 * 
 * @author ruoyi
 * @date 2025-05-24
 */
@RestController
@RequestMapping("/platform/userDeactivate")
public class UserDeactivateController extends BaseController
{
    @Autowired
    private IUserDeactivateService userDeactivateService;

    /**
     * 查询用户停用记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:userDeactivate:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserDeactivate userDeactivate)
    {
        startPage();
        List<UserDeactivate> list = userDeactivateService.selectUserDeactivateList(userDeactivate);
        return getDataTable(list);
    }

    /**
     * 导出用户停用记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:userDeactivate:export')")
    @Log(title = "用户停用记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserDeactivate userDeactivate)
    {
        List<UserDeactivate> list = userDeactivateService.selectUserDeactivateList(userDeactivate);
        ExcelUtil<UserDeactivate> util = new ExcelUtil<UserDeactivate>(UserDeactivate.class);
        util.exportExcel(response, list, "用户停用记录数据");
    }

    /**
     * 获取用户停用记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:userDeactivate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userDeactivateService.selectUserDeactivateById(id));
    }

    /**
     * 新增用户停用记录
     */
    @PreAuthorize("@ss.hasPermi('platform:userDeactivate:add')")
    @Log(title = "用户停用记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserDeactivate userDeactivate)
    {
        return toAjax(userDeactivateService.insertUserDeactivate(userDeactivate));
    }

    /**
     * 修改用户停用记录
     */
    @PreAuthorize("@ss.hasPermi('platform:userDeactivate:edit')")
    @Log(title = "用户停用记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserDeactivate userDeactivate)
    {
        return toAjax(userDeactivateService.updateUserDeactivate(userDeactivate));
    }

    /**
     * 删除用户停用记录
     */
    @PreAuthorize("@ss.hasPermi('platform:userDeactivate:remove')")
    @Log(title = "用户停用记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userDeactivateService.deleteUserDeactivateByIds(ids));
    }
}
