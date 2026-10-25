package com.ruoyi.web.controller.platform;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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
import com.ruoyi.platform.domain.IncomeWay;
import com.ruoyi.platform.service.IIncomeWayService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 收益方式Controller
 *
 * @author ruoyi
 * @date 2025-05-15
 */
@RestController
@RequestMapping("/platform/IncomeWay")
public class IncomeWayController extends BaseController {
    @Autowired
    private IIncomeWayService incomeWayService;

    /**
     * 查询收益方式列表
     */
    @PreAuthorize("@ss.hasPermi('platform:IncomeWay:list')")
    @GetMapping("/list")
    public TableDataInfo list(IncomeWay incomeWay) {
        startPage();
        List<IncomeWay> list = incomeWayService.selectIncomeWayList(incomeWay);
        return getDataTable(list);
    }

    /**
     * 导出收益方式列表
     */
    @PreAuthorize("@ss.hasPermi('platform:IncomeWay:export')")
    @Log(title = "收益方式", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IncomeWay incomeWay) {
        List<IncomeWay> list = incomeWayService.selectIncomeWayList(incomeWay);
        ExcelUtil<IncomeWay> util = new ExcelUtil<IncomeWay>(IncomeWay.class);
        util.exportExcel(response, list, "收益方式数据");
    }

    /**
     * 获取收益方式详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:IncomeWay:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(incomeWayService.selectIncomeWayById(id));
    }

    /**
     * 新增收益方式
     */
    @PreAuthorize("@ss.hasPermi('platform:IncomeWay:add')")
    @Log(title = "收益方式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IncomeWay incomeWay) {
        return toAjax(incomeWayService.insertIncomeWay(incomeWay));
    }

    /**
     * 修改收益方式
     */
    @PreAuthorize("@ss.hasPermi('platform:IncomeWay:edit')")
    @Log(title = "收益方式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IncomeWay incomeWay) {
        if (incomeWay.getType() == 1) {
            incomeWay.setMoneyMin(BigDecimal.ZERO);
            incomeWay.setMoneyMax(BigDecimal.ZERO);
        } else {
            if (Objects.isNull(incomeWay.getMoneyMin())||Objects.isNull(incomeWay.getMoneyMax())) {
                return AjaxResult.error("金额信息不正确");
            }
            if (incomeWay.getMoneyMin().compareTo(incomeWay.getMoneyMax()) >= 0) {
                return AjaxResult.error("金额信息填写不正确");
            }
        }
        return toAjax(incomeWayService.updateIncomeWay(incomeWay));
    }

    /**
     * 删除收益方式
     */
    @PreAuthorize("@ss.hasPermi('platform:IncomeWay:remove')")
    @Log(title = "收益方式", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(incomeWayService.deleteIncomeWayByIds(ids));
    }
}
