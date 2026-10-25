package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.DelStatusEnum;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.Drama;
import com.ruoyi.platform.domain.DramaCategory;
import com.ruoyi.platform.service.IDramaCategoryService;
import com.ruoyi.platform.service.IDramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 视频信息Controller
 *
 * @author ruoyi
 * @date 2024-11-10
 */
@RestController
@RequestMapping("/platform/drama")
public class DramaController extends BaseController {
    @Autowired
    private IDramaService dramaService;
    @Autowired
    private IDramaCategoryService dramaCategoryService;

    /**
     * 查询视频信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:drama:list')")
    @GetMapping("/list")
    public TableDataInfo list(Drama drama) {
        startPage();
        List<Drama> list = dramaService.selectDramaList(drama);
        return getDataTable(list);
    }

    /**
     * 导出视频信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:drama:export')")
    @Log(title = "视频信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Drama drama) {
        List<Drama> list = dramaService.selectDramaList(drama);
        ExcelUtil<Drama> util = new ExcelUtil<Drama>(Drama.class);
        util.exportExcel(response, list, "视频信息数据");
    }

    /**
     * 获取视频信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:drama:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(dramaService.selectDramaById(id));
    }

    /**
     * 新增视频信息
     */
    @PreAuthorize("@ss.hasPermi('platform:drama:add')")
    @Log(title = "视频信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Drama drama) {
        //查询该分类是否存在
        DramaCategory dramaCategory = dramaCategoryService.selectDramaCategoryById(drama.getClassifyId());
        if (Objects.isNull(dramaCategory) || DelStatusEnum.STATUS_0.getStatus().equals(dramaCategory.getDelStatus())) {
            return AjaxResult.error("您选择的分类信息不存在");
        }
        Date date = new Date();
        drama.setCreateTime(date);
        drama.setUpTime(date);
        return toAjax(dramaService.insertDrama(drama));
    }

    /**
     * 修改视频信息
     */
    @PreAuthorize("@ss.hasPermi('platform:drama:edit')")
    @Log(title = "视频信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Drama drama) {
        //查询该分类是否存在
        DramaCategory dramaCategory = dramaCategoryService.selectDramaCategoryById(drama.getClassifyId());
        if (Objects.isNull(dramaCategory) || DelStatusEnum.STATUS_0.getStatus().equals(dramaCategory.getDelStatus())) {
            return AjaxResult.error("您选择的分类信息不存在");
        }
        drama.setUpdateTime(new Date());
        return toAjax(dramaService.updateDrama(drama));
    }

    /**
     * 删除视频信息
     */
    @PreAuthorize("@ss.hasPermi('platform:drama:remove')")
    @Log(title = "视频信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dramaService.deleteDramaByIds(ids));
    }

    /**
     * 下架视频
     */
    @PreAuthorize("@ss.hasPermi('platform:drama:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/undercarriage/{ids}")
    public AjaxResult undercarriage(@PathVariable Long[] ids) {
        return toAjax(dramaService.undercarriage(ids));
    }

    /**
     * 上架
     */
    @PreAuthorize("@ss.hasPermi('platform:drama:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/grounding/{ids}")
    public AjaxResult grounding(@PathVariable Long[] ids) {
        return toAjax(dramaService.grounding(ids));
    }


    @GetMapping("/listInfo")
    public AjaxResult listInfo(Drama drama) {
        List<Drama> dramaInfo = dramaService.getDramaInfo();
        return AjaxResult.success(dramaInfo);
    }
}
