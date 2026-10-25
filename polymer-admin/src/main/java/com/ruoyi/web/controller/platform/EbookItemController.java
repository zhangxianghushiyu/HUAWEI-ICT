package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.EbookItem;
import com.ruoyi.platform.domain.MusicStore;
import com.ruoyi.platform.service.IEbookItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 电子书章节Controller
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
@RestController
@RequestMapping("/platform/ebookItem")
public class EbookItemController extends BaseController
{
    @Autowired
    private IEbookItemService ebookItemService;

    /**
     * 查询电子书章节列表
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(EbookItem ebookItem)
    {
        startPage();
        List<EbookItem> list = ebookItemService.selectEbookItemList(ebookItem);
        return getDataTable(list);
    }

    /**
     * 导出电子书章节列表
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookItem:export')")
    @Log(title = "电子书章节", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EbookItem ebookItem)
    {
        List<EbookItem> list = ebookItemService.selectEbookItemList(ebookItem);
        ExcelUtil<EbookItem> util = new ExcelUtil<EbookItem>(EbookItem.class);
        util.exportExcel(response, list, "电子书章节数据");
    }

    /**
     * 获取电子书章节详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ebookItemService.selectEbookItemById(id));
    }

    /**
     * 新增电子书章节
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookItem:add')")
    @Log(title = "电子书章节", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EbookItem ebookItem)
    {
        return toAjax(ebookItemService.insertEbookItem(ebookItem));
    }

    /**
     * 修改电子书章节
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookItem:edit')")
    @Log(title = "电子书章节", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EbookItem ebookItem)
    {
        return toAjax(ebookItemService.updateEbookItem(ebookItem));
    }

    /**
     * 删除电子书章节
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookItem:remove')")
    @Log(title = "电子书章节", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ebookItemService.deleteEbookItemByIds(ids));
    }



    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<EbookItem> util = new ExcelUtil<EbookItem>(EbookItem.class);
        util.importTemplateExcel(response, "数据");
    }

    @Log(title = "数据管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, Long ebookId) throws Exception {
        ExcelUtil<EbookItem> util = new ExcelUtil<>(EbookItem.class);
        List<EbookItem> dataHandles = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = ebookItemService.importDataHandle(dataHandles, updateSupport,
                operName,ebookId);
        return AjaxResult.success(message);
    }

    /**
     * 下架
     */
    @DeleteMapping("/undercarriage/{ids}")
    public AjaxResult undercarriage(@PathVariable Long[] ids) {
        return toAjax(ebookItemService.undercarriage(ids));
    }

    /**
     * 上架
     */
    @DeleteMapping("/grounding/{ids}")
    public AjaxResult grounding(@PathVariable Long[] ids) {
        return toAjax(ebookItemService.grounding(ids));
    }
}
