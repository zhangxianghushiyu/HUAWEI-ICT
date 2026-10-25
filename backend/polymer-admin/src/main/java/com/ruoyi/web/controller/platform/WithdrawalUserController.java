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
import com.ruoyi.platform.domain.WithdrawalUser;
import com.ruoyi.platform.service.IWithdrawalUserService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户提现绑定信息Controller
 * 
 * @author ruoyi
 * @date 2024-11-15
 */
@RestController
@RequestMapping("/platform/withdrawalUser")
public class WithdrawalUserController extends BaseController
{
    @Autowired
    private IWithdrawalUserService withdrawalUserService;

    /**
     * 查询用户提现绑定信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(WithdrawalUser withdrawalUser)
    {
        startPage();
        List<WithdrawalUser> list = withdrawalUserService.selectWithdrawalUserList(withdrawalUser);
        return getDataTable(list);
    }

    /**
     * 导出用户提现绑定信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalUser:export')")
    @Log(title = "用户提现绑定信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WithdrawalUser withdrawalUser)
    {
        List<WithdrawalUser> list = withdrawalUserService.selectWithdrawalUserList(withdrawalUser);
        ExcelUtil<WithdrawalUser> util = new ExcelUtil<WithdrawalUser>(WithdrawalUser.class);
        util.exportExcel(response, list, "用户提现绑定信息数据");
    }

    /**
     * 获取用户提现绑定信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(withdrawalUserService.selectWithdrawalUserById(id));
    }

    /**
     * 新增用户提现绑定信息
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalUser:add')")
    @Log(title = "用户提现绑定信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WithdrawalUser withdrawalUser)
    {
        return toAjax(withdrawalUserService.insertWithdrawalUser(withdrawalUser));
    }

    /**
     * 修改用户提现绑定信息
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalUser:edit')")
    @Log(title = "用户提现绑定信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WithdrawalUser withdrawalUser)
    {
        return toAjax(withdrawalUserService.updateWithdrawalUser(withdrawalUser));
    }

    /**
     * 删除用户提现绑定信息
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalUser:remove')")
    @Log(title = "用户提现绑定信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(withdrawalUserService.deleteWithdrawalUserByIds(ids));
    }
}
