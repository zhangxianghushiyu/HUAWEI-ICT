package com.ruoyi.web.controller.platform;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.dto.VideoLikeListDTO;
import com.ruoyi.platform.vo.VideoLikeListVO;
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
import com.ruoyi.platform.domain.VideoLike;
import com.ruoyi.platform.service.IVideoLikeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 视频点赞Controller
 *
 * @author ruoyi
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/platform/videoLike")
public class VideoLikeController extends BaseController
{
    @Autowired
    private IVideoLikeService videoLikeService;

    /**
     * 查询视频点赞列表
     */
    @PreAuthorize("@ss.hasPermi('platform:videoLike:list')")
    @GetMapping("/list")
    public TableDataInfo list(VideoLikeListDTO videoLike)
    {
        startPage();
        List<VideoLikeListVO> videoLikeListVOS = videoLikeService.selectVideoLikeListVO(videoLike);
        return getDataTable(videoLikeListVOS);
    }

    /**
     * 导出视频点赞列表
     */
    @PreAuthorize("@ss.hasPermi('platform:videoLike:export')")
    @Log(title = "视频点赞", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VideoLike videoLike)
    {
        List<VideoLike> list = videoLikeService.selectVideoLikeList(videoLike);
        ExcelUtil<VideoLike> util = new ExcelUtil<VideoLike>(VideoLike.class);
        util.exportExcel(response, list, "视频点赞数据");
    }

    /**
     * 获取视频点赞详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:videoLike:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(videoLikeService.selectVideoLikeById(id));
    }

    /**
     * 新增视频点赞
     */
    @PreAuthorize("@ss.hasPermi('platform:videoLike:add')")
    @Log(title = "视频点赞", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VideoLike videoLike)
    {
        return toAjax(videoLikeService.insertVideoLike(videoLike));
    }

    /**
     * 修改视频点赞
     */
    @PreAuthorize("@ss.hasPermi('platform:videoLike:edit')")
    @Log(title = "视频点赞", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VideoLike videoLike)
    {
        return toAjax(videoLikeService.updateVideoLike(videoLike));
    }

    /**
     * 删除视频点赞
     */
    @PreAuthorize("@ss.hasPermi('platform:videoLike:remove')")
    @Log(title = "视频点赞", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(videoLikeService.deleteVideoLikeByIds(ids));
    }
}
