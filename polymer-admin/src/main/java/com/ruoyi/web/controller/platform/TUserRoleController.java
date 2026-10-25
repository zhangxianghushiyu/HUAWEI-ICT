package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.TUserRole;
import com.ruoyi.platform.service.ITUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户身份Controller
 *
 * @author ruoyi
 * @date 2025-07-02
 */
@RestController
@RequestMapping("/platform/role")
public class TUserRoleController extends BaseController {
    @Autowired
    private ITUserRoleService tUserRoleService;

    /**
     * 查询用户身份列表
     */
    @PreAuthorize("@ss.hasPermi('platform:role:list')")
    @GetMapping("/list")
    public TableDataInfo list(TUserRole tUserRole) {
        startPage();
        List<TUserRole> list = tUserRoleService.selectTUserRoleList(tUserRole);
        return getDataTable(list);
    }

    /**
     * 导出用户身份列表
     */
    @PreAuthorize("@ss.hasPermi('platform:role:export')")
    @Log(title = "用户身份", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TUserRole tUserRole) {
        List<TUserRole> list = tUserRoleService.selectTUserRoleList(tUserRole);
        ExcelUtil<TUserRole> util = new ExcelUtil<TUserRole>(TUserRole.class);
        util.exportExcel(response, list, "用户身份数据");
    }

    /**
     * 获取用户身份详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:role:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tUserRoleService.selectTUserRoleById(id));
    }

    /**
     * 新增用户身份
     */
    @PreAuthorize("@ss.hasPermi('platform:role:add')")
    @Log(title = "用户身份", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TUserRole tUserRole) {
        return toAjax(tUserRoleService.insertTUserRole(tUserRole));
    }

    /**
     * 修改用户身份
     */
    @PreAuthorize("@ss.hasPermi('platform:role:edit')")
    @Log(title = "用户身份", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TUserRole tUserRole) {
        return toAjax(tUserRoleService.updateTUserRole(tUserRole));
    }

    /**
     * 删除用户身份
     */
    @PreAuthorize("@ss.hasPermi('platform:role:remove')")
    @Log(title = "用户身份", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tUserRoleService.deleteTUserRoleByIds(ids));
    }
}
