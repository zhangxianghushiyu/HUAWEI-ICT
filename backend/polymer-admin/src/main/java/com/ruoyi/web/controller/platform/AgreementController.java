package com.ruoyi.web.controller.platform;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.platform.domain.Agreement;
import com.ruoyi.platform.service.IAgreementService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 协议信息Controller
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/platform/agreement")
public class AgreementController extends BaseController
{
    @Autowired
    private IAgreementService agreementService;

    /**
     * 查询协议信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:agreement:list')")
    @GetMapping("/list")
    public TableDataInfo list(Agreement agreement)
    {
        startPage();
        List<Agreement> list = agreementService.selectAgreementList(agreement);
        return getDataTable(list);
    }

    /**
     * 导出协议信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:agreement:export')")
    @Log(title = "协议信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Agreement agreement)
    {
        List<Agreement> list = agreementService.selectAgreementList(agreement);
        ExcelUtil<Agreement> util = new ExcelUtil<Agreement>(Agreement.class);
        util.exportExcel(response, list, "协议信息数据");
    }

    /**
     * 获取协议信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:agreement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(agreementService.selectAgreementById(id));
    }

    /**
     * 新增协议信息
     */
    @PreAuthorize("@ss.hasPermi('platform:agreement:add')")
    @Log(title = "协议信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Agreement agreement)
    {
        if (StringUtils.isEmpty(agreement.getContent())){
            return AjaxResult.error("内容不能为空");
        }
        agreement.setCreateTime(new Date());
        //查看是不是已经存在相同类型的协议
        Agreement agreementByType = agreementService.getAgreementByType(agreement.getType());
        if (Objects.nonNull(agreementByType)){
            return AjaxResult.error("已经存在相同类型的信息");
        }
        return toAjax(agreementService.insertAgreement(agreement));
    }

    /**
     * 修改协议信息
     */
    @PreAuthorize("@ss.hasPermi('platform:agreement:edit')")
    @Log(title = "协议信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Agreement agreement)
    {
        if (StringUtils.isEmpty(agreement.getContent())){
            return AjaxResult.error("内容不能为空");
        }
        agreement.setUpdateTime(new Date());
        return toAjax(agreementService.updateAgreement(agreement));
    }

    /**
     * 删除协议信息
     */
    @PreAuthorize("@ss.hasPermi('platform:agreement:remove')")
    @Log(title = "协议信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(agreementService.deleteAgreementByIds(ids));
    }
}
