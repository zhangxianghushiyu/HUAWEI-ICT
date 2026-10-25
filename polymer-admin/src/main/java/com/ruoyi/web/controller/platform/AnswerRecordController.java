package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.AnswerRecord;
import com.ruoyi.platform.service.IAnswerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 答题记录Controller
 * 
 * @author ruoyi
 * @date 2025-02-14
 */
@RestController
@RequestMapping("/platform/answerRecord")
public class AnswerRecordController extends BaseController
{
    @Autowired
    private IAnswerRecordService answerRecordService;

    /**
     * 查询答题记录列表
     */
//    @PreAuthorize("@ss.hasPermi('platform:answerRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(AnswerRecord answerRecord)
    {
        startPage();
        List<AnswerRecord> list = answerRecordService.selectAnswerRecordList(answerRecord);
        return getDataTable(list);
    }

    /**
     * 导出答题记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:answerRecord:export')")
    @Log(title = "答题记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AnswerRecord answerRecord)
    {
        List<AnswerRecord> list = answerRecordService.selectAnswerRecordList(answerRecord);
        ExcelUtil<AnswerRecord> util = new ExcelUtil<AnswerRecord>(AnswerRecord.class);
        util.exportExcel(response, list, "答题记录数据");
    }

    /**
     * 获取答题记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:answerRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(answerRecordService.selectAnswerRecordById(id));
    }

    /**
     * 新增答题记录
     */
    @PreAuthorize("@ss.hasPermi('platform:answerRecord:add')")
    @Log(title = "答题记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AnswerRecord answerRecord)
    {
        return toAjax(answerRecordService.insertAnswerRecord(answerRecord));
    }

    /**
     * 修改答题记录
     */
    @PreAuthorize("@ss.hasPermi('platform:answerRecord:edit')")
    @Log(title = "答题记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AnswerRecord answerRecord)
    {
        return toAjax(answerRecordService.updateAnswerRecord(answerRecord));
    }

    /**
     * 删除答题记录
     */
    @PreAuthorize("@ss.hasPermi('platform:answerRecord:remove')")
    @Log(title = "答题记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(answerRecordService.deleteAnswerRecordByIds(ids));
    }
}
