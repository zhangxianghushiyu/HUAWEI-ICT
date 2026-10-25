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
import com.ruoyi.platform.domain.VoteRecord;
import com.ruoyi.platform.service.IVoteRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 投票记录Controller
 * 
 * @author lhr
 * @date 2026-05-15
 */
@RestController
@RequestMapping("/platform/record")
public class VoteRecordController extends BaseController
{
    @Autowired
    private IVoteRecordService voteRecordService;

    /**
     * 查询投票记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(VoteRecord voteRecord)
    {
        startPage();
        List<VoteRecord> list = voteRecordService.selectVoteRecordList(voteRecord);
        return getDataTable(list);
    }

    /**
     * 导出投票记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:record:export')")
    @Log(title = "投票记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VoteRecord voteRecord)
    {
        List<VoteRecord> list = voteRecordService.selectVoteRecordList(voteRecord);
        ExcelUtil<VoteRecord> util = new ExcelUtil<VoteRecord>(VoteRecord.class);
        util.exportExcel(response, list, "投票记录数据");
    }

    /**
     * 获取投票记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(voteRecordService.selectVoteRecordById(id));
    }

    /**
     * 新增投票记录
     */
    @PreAuthorize("@ss.hasPermi('platform:record:add')")
    @Log(title = "投票记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VoteRecord voteRecord)
    {
        return toAjax(voteRecordService.insertVoteRecord(voteRecord));
    }

    /**
     * 修改投票记录
     */
    @PreAuthorize("@ss.hasPermi('platform:record:edit')")
    @Log(title = "投票记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VoteRecord voteRecord)
    {
        return toAjax(voteRecordService.updateVoteRecord(voteRecord));
    }

    /**
     * 删除投票记录
     */
    @PreAuthorize("@ss.hasPermi('platform:record:remove')")
    @Log(title = "投票记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(voteRecordService.deleteVoteRecordByIds(ids));
    }
}
