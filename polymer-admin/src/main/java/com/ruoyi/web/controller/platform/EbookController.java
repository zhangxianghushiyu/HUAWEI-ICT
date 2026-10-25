package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.Ebook;
import com.ruoyi.platform.service.IEbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 电子书主信息Controller
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
@RestController
@RequestMapping("/platform/ebook")
public class EbookController extends BaseController
{
    @Autowired
    private IEbookService ebookService;

    /**
     * 查询电子书主信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:ebook:list')")
    @GetMapping("/list")
    public TableDataInfo list(Ebook ebook)
    {
        startPage();
        List<Ebook> list = ebookService.selectEbookList(ebook);
        return getDataTable(list);
    }

    /**
     * 导出电子书主信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:ebook:export')")
    @Log(title = "电子书主信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Ebook ebook)
    {
        List<Ebook> list = ebookService.selectEbookList(ebook);
        ExcelUtil<Ebook> util = new ExcelUtil<Ebook>(Ebook.class);
        util.exportExcel(response, list, "电子书主信息数据");
    }

    /**
     * 获取电子书主信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:ebook:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ebookService.selectEbookById(id));
    }

    /**
     * 新增电子书主信息
     */
    @PreAuthorize("@ss.hasPermi('platform:ebook:add')")
    @Log(title = "电子书主信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Ebook ebook)
    {
        return toAjax(ebookService.insertEbook(ebook));
    }

    /**
     * 修改电子书主信息
     */
    @PreAuthorize("@ss.hasPermi('platform:ebook:edit')")
    @Log(title = "电子书主信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Ebook ebook)
    {
        return toAjax(ebookService.updateEbook(ebook));
    }

    /**
     * 删除电子书主信息
     */
    @PreAuthorize("@ss.hasPermi('platform:ebook:remove')")
    @Log(title = "电子书主信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ebookService.deleteEbookByIds(ids));
    }


    /**
     * 下架
     */
    @DeleteMapping("/undercarriage/{ids}")
    public AjaxResult undercarriage(@PathVariable Long[] ids) {
        return toAjax(ebookService.undercarriage(ids));
    }

    /**
     * 上架
     */
    @DeleteMapping("/grounding/{ids}")
    public AjaxResult grounding(@PathVariable Long[] ids) {
        return toAjax(ebookService.grounding(ids));
    }
}
