package com.ruoyi.web.controller.platform;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.dto.VideoCollectListDTO;
import com.ruoyi.platform.vo.VideoCollectListVO;
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
import com.ruoyi.platform.domain.VideoCollect;
import com.ruoyi.platform.service.IVideoCollectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户收藏短视频Controller
 *
 * @author ruoyi
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/platform/videoCollect")
public class VideoCollectController extends BaseController
{
    @Autowired
    private IVideoCollectService videoCollectService;

    /**
     * 查询用户收藏短视频列表
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCollect:list')")
    @GetMapping("/list")
    public TableDataInfo list(VideoCollectListDTO videoCollect)
    {
        startPage();
        List<VideoCollectListVO> list = videoCollectService.selectVideoCollectListVO(videoCollect);
        return getDataTable(list);
    }

    /**
     * 导出用户收藏短视频列表
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCollect:export')")
    @Log(title = "用户收藏短视频", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VideoCollect videoCollect)
    {
        List<VideoCollect> list = videoCollectService.selectVideoCollectList(videoCollect);
        ExcelUtil<VideoCollect> util = new ExcelUtil<VideoCollect>(VideoCollect.class);
        util.exportExcel(response, list, "用户收藏短视频数据");
    }

    /**
     * 获取用户收藏短视频详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCollect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(videoCollectService.selectVideoCollectById(id));
    }

    /**
     * 新增用户收藏短视频
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCollect:add')")
    @Log(title = "用户收藏短视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VideoCollect videoCollect)
    {
        return toAjax(videoCollectService.insertVideoCollect(videoCollect));
    }

    /**
     * 修改用户收藏短视频
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCollect:edit')")
    @Log(title = "用户收藏短视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VideoCollect videoCollect)
    {
        return toAjax(videoCollectService.updateVideoCollect(videoCollect));
    }

    /**
     * 删除用户收藏短视频
     */
    @PreAuthorize("@ss.hasPermi('platform:videoCollect:remove')")
    @Log(title = "用户收藏短视频", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(videoCollectService.deleteVideoCollectByIds(ids));
    }
}
