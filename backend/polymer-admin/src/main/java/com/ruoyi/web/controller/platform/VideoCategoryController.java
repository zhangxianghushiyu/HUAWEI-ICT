package com.ruoyi.web.controller.platform;

import java.util.Date;
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
import com.ruoyi.platform.domain.VideoCategory;
import com.ruoyi.platform.service.IVideoCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 视频分类Controller
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/platform/videoCategory")
public class VideoCategoryController extends BaseController {
    @Autowired
    private IVideoCategoryService videoCategoryService;

    /**
     * 查询视频分类列表
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(VideoCategory videoCategory) {
        startPage();
        List<VideoCategory> list = videoCategoryService.selectVideoCategoryList(videoCategory);
        return getDataTable(list);
    }

    /**
     * 导出视频分类列表
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCategory:export')")
    @Log(title = "视频分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VideoCategory videoCategory) {
        List<VideoCategory> list = videoCategoryService.selectVideoCategoryList(videoCategory);
        ExcelUtil<VideoCategory> util = new ExcelUtil<VideoCategory>(VideoCategory.class);
        util.exportExcel(response, list, "视频分类数据");
    }

    /**
     * 获取视频分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(videoCategoryService.selectVideoCategoryById(id));
    }

    /**
     * 新增视频分类
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCategory:add')")
    @Log(title = "视频分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VideoCategory videoCategory) {
        videoCategory.setCreateTime(new Date());
        return toAjax(videoCategoryService.insertVideoCategory(videoCategory));
    }

    /**
     * 修改视频分类
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCategory:edit')")
    @Log(title = "视频分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VideoCategory videoCategory) {
        videoCategory.setUpdateTime(new Date());
        return toAjax(videoCategoryService.updateVideoCategory(videoCategory));
    }

    /**
     * 删除视频分类
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCategory:remove')")
    @Log(title = "视频分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(videoCategoryService.deleteVideoCategoryByIds(ids));
    }
}
