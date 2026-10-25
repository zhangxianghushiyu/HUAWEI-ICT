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
import com.ruoyi.platform.domain.VoteOption;
import com.ruoyi.platform.service.IVoteOptionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 投票选项Controller
 * 
 * @author lhr
 * @date 2026-05-15
 */
@RestController
@RequestMapping("/platform/option")
public class VoteOptionController extends BaseController
{
    @Autowired
    private IVoteOptionService voteOptionService;

    /**
     * 查询投票选项列表
     */
    @PreAuthorize("@ss.hasPermi('platform:option:list')")
    @GetMapping("/list")
    public TableDataInfo list(VoteOption voteOption)
    {
        startPage();
        List<VoteOption> list = voteOptionService.selectVoteOptionList(voteOption);
        return getDataTable(list);
    }

    /**
     * 导出投票选项列表
     */
    @PreAuthorize("@ss.hasPermi('platform:option:export')")
    @Log(title = "投票选项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VoteOption voteOption)
    {
        List<VoteOption> list = voteOptionService.selectVoteOptionList(voteOption);
        ExcelUtil<VoteOption> util = new ExcelUtil<VoteOption>(VoteOption.class);
        util.exportExcel(response, list, "投票选项数据");
    }

    /**
     * 获取投票选项详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:option:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(voteOptionService.selectVoteOptionById(id));
    }

    /**
     * 新增投票选项
     */
    @PreAuthorize("@ss.hasPermi('platform:option:add')")
    @Log(title = "投票选项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VoteOption voteOption)
    {
        return toAjax(voteOptionService.insertVoteOption(voteOption));
    }

    /**
     * 修改投票选项
     */
    @PreAuthorize("@ss.hasPermi('platform:option:edit')")
    @Log(title = "投票选项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VoteOption voteOption)
    {
        return toAjax(voteOptionService.updateVoteOption(voteOption));
    }

    /**
     * 删除投票选项
     */
    @PreAuthorize("@ss.hasPermi('platform:option:remove')")
    @Log(title = "投票选项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(voteOptionService.deleteVoteOptionByIds(ids));
    }
}
