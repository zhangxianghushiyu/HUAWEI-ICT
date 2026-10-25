package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.Ebook;
import com.ruoyi.platform.domain.EbookCategory;
import com.ruoyi.platform.domain.EbookCategoryInfo;
import com.ruoyi.platform.domain.EbookItem;
import com.ruoyi.platform.service.IEbookCategoryInfoService;
import com.ruoyi.platform.service.IEbookCategoryService;
import com.ruoyi.platform.service.IEbookService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 分类及电子书关联Controller
 *
 * @author ruoyi
 * @date 2025-05-21
 */
@RestController
@RequestMapping("/platform/ebookCategoryInfo")
public class EbookCategoryInfoController extends BaseController {
    @Autowired
    private IEbookCategoryInfoService ebookCategoryInfoService;
    @Autowired
    private IEbookService ebookService;
    @Autowired
    private IEbookCategoryService ebookCategoryService;

    /**
     * 查询分类及电子书关联列表
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategoryInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(EbookCategoryInfo ebookCategoryInfo) {
        startPage();
        List<EbookCategoryInfo> list = ebookCategoryInfoService.selectEbookCategoryInfoList(ebookCategoryInfo);
        return getDataTable(list);
    }

    /**
     * 导出分类及电子书关联列表
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategoryInfo:export')")
    @Log(title = "分类及电子书关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EbookCategoryInfo ebookCategoryInfo) {
        List<EbookCategoryInfo> list = ebookCategoryInfoService.selectEbookCategoryInfoList(ebookCategoryInfo);
        ExcelUtil<EbookCategoryInfo> util = new ExcelUtil<EbookCategoryInfo>(EbookCategoryInfo.class);
        util.exportExcel(response, list, "分类及电子书关联数据");
    }

    /**
     * 获取分类及电子书关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategoryInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(ebookCategoryInfoService.selectEbookCategoryInfoById(id));
    }

    /**
     * 新增分类及电子书关联
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategoryInfo:add')")
    @Log(title = "分类及电子书关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EbookCategoryInfo ebookCategoryInfo) {
        return toAjax(ebookCategoryInfoService.insertEbookCategoryInfo(ebookCategoryInfo));
    }

    /**
     * 修改分类及电子书关联
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategoryInfo:edit')")
    @Log(title = "分类及电子书关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EbookCategoryInfo ebookCategoryInfo) {
        return toAjax(ebookCategoryInfoService.updateEbookCategoryInfo(ebookCategoryInfo));
    }

    /**
     * 删除分类及电子书关联
     */
    @PreAuthorize("@ss.hasPermi('platform:ebookCategoryInfo:remove')")
    @Log(title = "分类及电子书关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(ebookCategoryInfoService.deleteEbookCategoryInfoByIds(ids));
    }


    @GetMapping("/association")
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit(interval = 3000)
    public AjaxResult association(@Param("ebookId") Long ebookId, @Param("categoryIdStr") String categoryIdStr) {
        //查询电子书是不是存在
        Ebook ebook = ebookService.selectEbookById(ebookId);
        if (ebook == null || ebook.getDelStatus() == 0) {
            return AjaxResult.error("电子书信息不存在");
        }
        //查询分类是不是存在
        String[] split = categoryIdStr.split(",");
        for (String s : split) {
            long l = Long.parseLong(s);
            EbookCategory ebookCategory = ebookCategoryService.selectEbookCategoryById(l);
            if (Objects.isNull(ebookCategory)) {
                return AjaxResult.error("分类ID：" + s + " 不存在");
            }
            //查询次分类下是不是已经存在
            EbookCategoryInfo ebookCategoryInfo = new EbookCategoryInfo();
            ebookCategoryInfo.setCategoryId(l);
            ebookCategoryInfo.setEbookId(ebookId);
            List<EbookCategoryInfo> ebookCategoryInfos = ebookCategoryInfoService.selectEbookCategoryInfoList(ebookCategoryInfo);
            if (!ebookCategoryInfos.isEmpty()) {
                return AjaxResult.error("分类ID：" + s + " 下已存在此小说，无法进行重复添加");
            }
        }
        //新增数据
        for (String s : split) {
            long l = Long.parseLong(s);
            EbookCategoryInfo ebookCategoryInfo = new EbookCategoryInfo();
            ebookCategoryInfo.setCategoryId(l);
            ebookCategoryInfo.setEbookId(ebookId);
            ebookCategoryInfo.setCreateTime(new Date());
            ebookCategoryInfoService.insertEbookCategoryInfo(ebookCategoryInfo);
        }
        return AjaxResult.success();
    }
}
