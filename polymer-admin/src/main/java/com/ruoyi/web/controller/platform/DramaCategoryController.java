package com.ruoyi.web.controller.platform;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.service.IDramaService;
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
import com.ruoyi.platform.domain.DramaCategory;
import com.ruoyi.platform.service.IDramaCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短剧分类Controller
 *
 * @author ruoyi
 * @date 2024-11-10
 */
@RestController
@RequestMapping("/platform/dramaCategory")
public class DramaCategoryController extends BaseController {
    @Autowired
    private IDramaCategoryService dramaCategoryService;
    @Autowired
    private IDramaService dramaService;

    /**
     * 查询短剧分类列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(DramaCategory dramaCategory) {
        startPage();
        List<DramaCategory> list = dramaCategoryService.selectDramaCategoryList(dramaCategory);
        return getDataTable(list);
    }

    /**
     * 导出短剧分类列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCategory:export')")
    @Log(title = "短剧分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DramaCategory dramaCategory) {
        List<DramaCategory> list = dramaCategoryService.selectDramaCategoryList(dramaCategory);
        ExcelUtil<DramaCategory> util = new ExcelUtil<DramaCategory>(DramaCategory.class);
        util.exportExcel(response, list, "短剧分类数据");
    }

    /**
     * 获取短剧分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(dramaCategoryService.selectDramaCategoryById(id));
    }

    /**
     * 新增短剧分类
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCategory:add')")
    @Log(title = "短剧分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DramaCategory dramaCategory) {
        dramaCategory.setCreateTime(new Date());
        return toAjax(dramaCategoryService.insertDramaCategory(dramaCategory));
    }

    /**
     * 修改短剧分类
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCategory:edit')")
    @Log(title = "短剧分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DramaCategory dramaCategory) {
        dramaCategory.setUpdateTime(new Date());
        return toAjax(dramaCategoryService.updateDramaCategory(dramaCategory));
    }

    /**
     * 删除短剧分类
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCategory:remove')")
    @Log(title = "短剧分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        //查询分类下是否还有短剧
        for (Long id : ids) {
            Integer count = dramaCategoryService.selectDramaCategoryByParentId(id);
            if (count > 0) {
                return AjaxResult.error("该分类下存在二级分类，暂时无法删除");
            }
            count = dramaService.selectCountByClassifyId(id);
            if (count > 0) {
                return AjaxResult.error("该分类下存在短剧信息，暂时无法删除");
            }
        }
        return toAjax(dramaCategoryService.deleteDramaCategoryByIds(ids));
    }

    @GetMapping("/listInfo")
    public AjaxResult listInfo(DramaCategory category) {
        List<DramaCategory> list = dramaCategoryService.listInfo(category);
        return AjaxResult.success(list);
    }
}
