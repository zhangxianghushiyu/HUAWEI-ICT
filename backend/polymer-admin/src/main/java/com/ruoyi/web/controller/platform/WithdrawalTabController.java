package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.WithdrawalTab;
import com.ruoyi.platform.service.IWithdrawalTabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * 提现金额选项卡Controller
 *
 * @author ruoyi
 * @date 2024-11-14
 */
@RestController
@RequestMapping("/platform/withdrawalTab")
public class WithdrawalTabController extends BaseController {
    @Autowired
    private IWithdrawalTabService withdrawalTabService;

    /**
     * 查询提现金额选项卡列表
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalTab:list')")
    @GetMapping("/list")
    public TableDataInfo list(WithdrawalTab withdrawalTab) {
        startPage();
        List<WithdrawalTab> list = withdrawalTabService.selectWithdrawalTabList(withdrawalTab);
        return getDataTable(list);
    }

    /**
     * 导出提现金额选项卡列表
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalTab:export')")
    @Log(title = "提现金额选项卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WithdrawalTab withdrawalTab) {
        List<WithdrawalTab> list = withdrawalTabService.selectWithdrawalTabList(withdrawalTab);
        ExcelUtil<WithdrawalTab> util = new ExcelUtil<WithdrawalTab>(WithdrawalTab.class);
        util.exportExcel(response, list, "提现金额选项卡数据");
    }

    /**
     * 获取提现金额选项卡详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalTab:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(withdrawalTabService.selectWithdrawalTabById(id));
    }

    /**
     * 新增提现金额选项卡
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalTab:add')")
    @Log(title = "提现金额选项卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WithdrawalTab withdrawalTab) {
        //单笔限额
        //单笔转账限额，默认为0.1-200元
        //单用户限额
        //单商户号单日向同一用户的最高转账额度，默认为2,000元
        //单日转账额度
        //单商户号单日最高转账额度，默认为5万元
        //单月转账额度
        //单商户号单月最高转账额度，默认为3,000万元
        //如有提额需求请在开通后按需申请（商户平台>产品中心>运营工具 > 商家转账 > 前往功能）。提额结果将根据商户号历史使用情况和提额审核结果确定，建议使用一段时间后再申请提高。

        // 检查是否不小于0.1且小于200
        BigDecimal minValue = new BigDecimal("0.1");
        BigDecimal maxValue = new BigDecimal("200");
        // 检查是否不小于0.1且小于200
        if (withdrawalTab.getAmount().compareTo(minValue) >= 0 && withdrawalTab.getAmount().compareTo(maxValue) < 0) {

        } else {
            return AjaxResult.error("值应在0.1到200之间");
        }

        return toAjax(withdrawalTabService.insertWithdrawalTab(withdrawalTab));
    }

    /**
     * 修改提现金额选项卡
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalTab:edit')")
    @Log(title = "提现金额选项卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WithdrawalTab withdrawalTab) {
        //单笔限额
        //单笔转账限额，默认为0.1-200元
        //单用户限额
        //单商户号单日向同一用户的最高转账额度，默认为2,000元
        //单日转账额度
        //单商户号单日最高转账额度，默认为5万元
        //单月转账额度
        //单商户号单月最高转账额度，默认为3,000万元
        //如有提额需求请在开通后按需申请（商户平台>产品中心>运营工具 > 商家转账 > 前往功能）。提额结果将根据商户号历史使用情况和提额审核结果确定，建议使用一段时间后再申请提高。

        // 检查是否不小于0.1且小于200
        BigDecimal minValue = new BigDecimal("0.1");
        BigDecimal maxValue = new BigDecimal("200");
        // 检查是否不小于0.1且小于200
        if (withdrawalTab.getAmount().compareTo(minValue) >= 0 && withdrawalTab.getAmount().compareTo(maxValue) < 0) {

        } else {
            return AjaxResult.error("值应在0.1到200之间");
        }
        return toAjax(withdrawalTabService.updateWithdrawalTab(withdrawalTab));
    }

    /**
     * 删除提现金额选项卡
     */
    @PreAuthorize("@ss.hasPermi('platform:withdrawalTab:remove')")
    @Log(title = "提现金额选项卡", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(withdrawalTabService.deleteWithdrawalTabByIds(ids));
    }
}
