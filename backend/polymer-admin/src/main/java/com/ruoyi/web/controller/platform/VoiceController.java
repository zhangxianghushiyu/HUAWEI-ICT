package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.Voice;
import com.ruoyi.platform.service.IVoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 语音信息Controller
 * 
 * @author ruoyi
 * @date 2025-02-20
 */
@RestController
@RequestMapping("/platform/voice")
public class VoiceController extends BaseController
{
    @Autowired
    private IVoiceService voiceService;

    /**
     * 查询语音信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:voice:list')")
    @GetMapping("/list")
    public TableDataInfo list(Voice voice)
    {
        startPage();
        List<Voice> list = voiceService.selectVoiceList(voice);
        return getDataTable(list);
    }

    /**
     * 导出语音信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:voice:export')")
    @Log(title = "语音信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Voice voice)
    {
        List<Voice> list = voiceService.selectVoiceList(voice);
        ExcelUtil<Voice> util = new ExcelUtil<Voice>(Voice.class);
        util.exportExcel(response, list, "语音信息数据");
    }

    /**
     * 获取语音信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:voice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(voiceService.selectVoiceById(id));
    }

    /**
     * 新增语音信息
     */
    @PreAuthorize("@ss.hasPermi('platform:voice:add')")
    @Log(title = "语音信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Voice voice)
    {
        return toAjax(voiceService.insertVoice(voice));
    }

    /**
     * 修改语音信息
     */
    @PreAuthorize("@ss.hasPermi('platform:voice:edit')")
    @Log(title = "语音信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Voice voice)
    {
        return toAjax(voiceService.updateVoice(voice));
    }

    /**
     * 删除语音信息
     */
    @PreAuthorize("@ss.hasPermi('platform:voice:remove')")
    @Log(title = "语音信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(voiceService.deleteVoiceByIds(ids));
    }
}
