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
import com.ruoyi.platform.domain.WithdrawalInterval;
import com.ruoyi.platform.service.IWithdrawalIntervalService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 提现间隔配置Controller
 *
 * @author ruoyi
 * @date 2024-12-03
 */
@RestController
@RequestMapping("/platform/withdrawalInterval")
public class WithdrawalIntervalController extends BaseController
{
    @Autowired
    private IWithdrawalIntervalService withdrawalIntervalService;

    /**
     * 查询提现间隔配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalInterval:list')")
    @GetMapping("/list")
    public TableDataInfo list(WithdrawalInterval withdrawalInterval)
    {
        startPage();
        List<WithdrawalInterval> list = withdrawalIntervalService.selectWithdrawalIntervalList(withdrawalInterval);
        return getDataTable(list);
    }

    /**
     * 导出提现间隔配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalInterval:export')")
    @Log(title = "提现间隔配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WithdrawalInterval withdrawalInterval)
    {
        List<WithdrawalInterval> list = withdrawalIntervalService.selectWithdrawalIntervalList(withdrawalInterval);
        ExcelUtil<WithdrawalInterval> util = new ExcelUtil<WithdrawalInterval>(WithdrawalInterval.class);
        util.exportExcel(response, list, "提现间隔配置数据");
    }

    /**
     * 获取提现间隔配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalInterval:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(withdrawalIntervalService.selectWithdrawalIntervalById(id));
    }

    /**
     * 新增提现间隔配置
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalInterval:add')")
    @Log(title = "提现间隔配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WithdrawalInterval withdrawalInterval)
    {
        return toAjax(withdrawalIntervalService.insertWithdrawalInterval(withdrawalInterval));
    }

    /**
     * 修改提现间隔配置
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalInterval:edit')")
    @Log(title = "提现间隔配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WithdrawalInterval withdrawalInterval)
    {
        if (withdrawalInterval.getDay()<0){
            return AjaxResult.error("请输入正确的天数");
        }
        return toAjax(withdrawalIntervalService.updateWithdrawalInterval(withdrawalInterval));
    }

    /**
     * 删除提现间隔配置
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalInterval:remove')")
    @Log(title = "提现间隔配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(withdrawalIntervalService.deleteWithdrawalIntervalByIds(ids));
    }
}
