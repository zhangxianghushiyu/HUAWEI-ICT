package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.EbookCategory;
import com.ruoyi.platform.service.IEbookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 电子书分类Controller
 *
 * @author ruoyi
 * @date 2025-05-21
 */
@RestController
@RequestMapping("/platform/ebookCategory")
public class EbookCategoryController extends BaseController {
    @Autowired
    private IEbookCategoryService ebookCategoryService;

    /**
     * 查询电子书分类列表
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(EbookCategory ebookCategory) {
        startPage();
        List<EbookCategory> list = ebookCategoryService.selectEbookCategoryList(ebookCategory);
        return getDataTable(list);
    }

    /**
     * 导出电子书分类列表
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategory:export')")
    @Log(title = "电子书分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EbookCategory ebookCategory) {
        List<EbookCategory> list = ebookCategoryService.selectEbookCategoryList(ebookCategory);
        ExcelUtil<EbookCategory> util = new ExcelUtil<EbookCategory>(EbookCategory.class);
        util.exportExcel(response, list, "电子书分类数据");
    }

    /**
     * 获取电子书分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(ebookCategoryService.selectEbookCategoryById(id));
    }

    /**
     * 新增电子书分类
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategory:add')")
    @Log(title = "电子书分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EbookCategory ebookCategory) {
        return toAjax(ebookCategoryService.insertEbookCategory(ebookCategory));
    }

    /**
     * 修改电子书分类
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategory:edit')")
    @Log(title = "电子书分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EbookCategory ebookCategory) {
        return toAjax(ebookCategoryService.updateEbookCategory(ebookCategory));
    }

    /**
     * 删除电子书分类
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategory:remove')")
    @Log(title = "电子书分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(ebookCategoryService.deleteEbookCategoryByIds(ids));
    }

}
