package com.ruoyi.web.controller.platform;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.dto.FeedbackListDTO;
import com.ruoyi.platform.vo.FeedbackListVO;
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
import com.ruoyi.platform.domain.Feedback;
import com.ruoyi.platform.service.IFeedbackService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 帮助反馈信息Controller
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/platform/feedback")
public class FeedbackController extends BaseController
{
    @Autowired
    private IFeedbackService feedbackService;

    /**
     * 查询帮助反馈信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:feedback:list')")
    @GetMapping("/list")
    public TableDataInfo list(FeedbackListDTO feedback)
    {
        startPage();
        List<FeedbackListVO> list = feedbackService.getFeedbackListVO(feedback);
        return getDataTable(list);
    }

    /**
     * 导出帮助反馈信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:feedback:export')")
    @Log(title = "帮助反馈信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Feedback feedback)
    {
        List<Feedback> list = feedbackService.selectFeedbackList(feedback);
        ExcelUtil<Feedback> util = new ExcelUtil<Feedback>(Feedback.class);
        util.exportExcel(response, list, "帮助反馈信息数据");
    }

    /**
     * 获取帮助反馈信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:feedback:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(feedbackService.selectFeedbackById(id));
    }

    /**
     * 新增帮助反馈信息
     */
    @PreAuthorize("@ss.hasPermi('platform:feedback:add')")
    @Log(title = "帮助反馈信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Feedback feedback)
    {
        return toAjax(feedbackService.insertFeedback(feedback));
    }

    /**
     * 修改帮助反馈信息
     */
    @PreAuthorize("@ss.hasPermi('platform:feedback:edit')")
    @Log(title = "帮助反馈信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Feedback feedback)
    {
        return toAjax(feedbackService.updateFeedback(feedback));
    }

    /**
     * 删除帮助反馈信息
     */
    @PreAuthorize("@ss.hasPermi('platform:feedback:remove')")
    @Log(title = "帮助反馈信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(feedbackService.deleteFeedbackByIds(ids));
    }
}
