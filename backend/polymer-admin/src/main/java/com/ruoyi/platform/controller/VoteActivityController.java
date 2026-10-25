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
import com.ruoyi.platform.domain.VoteActivity;
import com.ruoyi.platform.service.IVoteActivityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 投票活动Controller
 * 
 * @author lhr
 * @date 2026-05-15
 */
@RestController
@RequestMapping("/platform/activity")
public class VoteActivityController extends BaseController
{
    @Autowired
    private IVoteActivityService voteActivityService;

    /**
     * 查询投票活动列表
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(VoteActivity voteActivity)
    {
        startPage();
        List<VoteActivity> list = voteActivityService.selectVoteActivityList(voteActivity);
        return getDataTable(list);
    }

    /**
     * 导出投票活动列表
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:export')")
    @Log(title = "投票活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VoteActivity voteActivity)
    {
        List<VoteActivity> list = voteActivityService.selectVoteActivityList(voteActivity);
        ExcelUtil<VoteActivity> util = new ExcelUtil<VoteActivity>(VoteActivity.class);
        util.exportExcel(response, list, "投票活动数据");
    }

    /**
     * 获取投票活动详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(voteActivityService.selectVoteActivityById(id));
    }

    /**
     * 新增投票活动
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:add')")
    @Log(title = "投票活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VoteActivity voteActivity)
    {
        return toAjax(voteActivityService.insertVoteActivity(voteActivity));
    }

    /**
     * 修改投票活动
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:edit')")
    @Log(title = "投票活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VoteActivity voteActivity)
    {
        return toAjax(voteActivityService.updateVoteActivity(voteActivity));
    }

    /**
     * 删除投票活动
     */
    @PreAuthorize("@ss.hasPermi('platform:activity:remove')")
    @Log(title = "投票活动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(voteActivityService.deleteVoteActivityByIds(ids));
    }
}
