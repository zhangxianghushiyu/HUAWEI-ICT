package com.ruoyi.web.controller.platform;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.dto.VideoListDTO;
import com.ruoyi.platform.dto.VideoUploadDTO;
import com.ruoyi.platform.vo.VideoListVO;
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
import com.ruoyi.platform.domain.Video;
import com.ruoyi.platform.service.IVideoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频Controller
 *
 * @author ruoyi
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/platform/video")
public class VideoController extends BaseController {
    @Autowired
    private IVideoService videoService;

    /**
     * 查询视频列表
     */
    @PreAuthorize("@ss.hasPermi('platform:video:list')")
    @GetMapping("/list")
    public TableDataInfo list(VideoListDTO video) {
        startPage();
        List<VideoListVO> list = videoService.selectVideoListVO(video);
        return getDataTable(list);
    }




    /**
     * 获取视频详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:video:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(videoService.selectVideoById(id));
    }

    /**
     * 新增视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:add')")
    @Log(title = "视频", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Video video) {
        return toAjax(videoService.insertVideo(video));
    }

    /**
     * 修改视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:edit')")
    @Log(title = "视频", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Video video) {
        return toAjax(videoService.updateVideo(video));
    }

    /**
     * 删除视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(videoService.deleteVideoByIds(ids));
    }

    /**
     * 下架视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/undercarriage/{ids}")
    public AjaxResult undercarriage(@PathVariable Long[] ids) {
        return toAjax(videoService.undercarriage(ids));
    }

    /**
     * 上架视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/grounding/{ids}")
    public AjaxResult grounding(@PathVariable Long[] ids) {
        return toAjax(videoService.grounding(ids));
    }

    /**
     * 查询系列名去重列表
     */
    @PreAuthorize("@ss.hasPermi('platform:video:list')")
    @GetMapping("/seriesNames")
    public AjaxResult seriesNames(Integer classifyId) {
        return AjaxResult.success(videoService.selectSeriesNameList(classifyId));
    }

    /**
     * 按分类删除视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/delByClassify/{classifyId}")
    public AjaxResult delByClassify(@PathVariable("classifyId") Integer classifyId) {
        return toAjax(videoService.delVideoByClassify(classifyId));
    }

    /**
     * 按系列删除视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/delBySeries/{seriesName}")
    public AjaxResult delBySeries(@PathVariable("seriesName") String seriesName) {
        return toAjax(videoService.delVideoBySeries(seriesName));
    }

    /**
     * 按分类上架视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/groundingByClassify/{classifyId}")
    public AjaxResult groundingByClassify(@PathVariable("classifyId") Integer classifyId) {
        return toAjax(videoService.groundingByClassify(classifyId));
    }

    /**
     * 按系列上架视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/groundingBySeries/{seriesName}")
    public AjaxResult groundingBySeries(@PathVariable("seriesName") String seriesName) {
        return toAjax(videoService.groundingBySeries(seriesName));
    }

    /**
     * 按分类下架视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/undercarriageByClassify/{classifyId}")
    public AjaxResult undercarriageByClassify(@PathVariable("classifyId") Integer classifyId) {
        return toAjax(videoService.undercarriageByClassify(classifyId));
    }

    /**
     * 按系列下架视频
     */
    @PreAuthorize("@ss.hasPermi('platform:video:remove')")
    @Log(title = "视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/undercarriageBySeries/{seriesName}")
    public AjaxResult undercarriageBySeries(@PathVariable("seriesName") String seriesName) {
        return toAjax(videoService.undercarriageBySeries(seriesName));
    }

    /**
     * 批量上传视频（关联系列名与分类）
     */
    @PreAuthorize("@ss.hasPermi('platform:video:add')")
    @Log(title = "视频上传", businessType = BusinessType.INSERT)
    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@RequestBody VideoUploadDTO dto) {
        return toAjax(videoService.batchAddVideos(dto.getClassifyId(), dto.getSeriesName(), dto.getVideos()));
    }


    /**
     * 批量解析并转码视频为 9:16（补录历史数据）
     */
    @PreAuthorize("@ss.hasPermi('platform:video:edit')")
    @PostMapping("/refreshVideoSize")
    public AjaxResult refreshVideoSize() {
        videoService.refreshVideoSize();
        return AjaxResult.success("已触发视频转码");
    }

    /**
     * 批量回填视频系列名与集数（补录历史数据）
     */
    @PreAuthorize("@ss.hasPermi('platform:video:edit')")
    @PostMapping("/refreshVideoSeries")
    public AjaxResult refreshVideoSeries() {
        videoService.refreshVideoSeries();
        return AjaxResult.success("已触发系列名回填");
    }



    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<VideoListVO> util = new ExcelUtil<VideoListVO>(VideoListVO.class);
        util.importTemplateExcel(response, "数据");
    }

    @Log(title = "数据管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<VideoListDTO> util = new ExcelUtil<>(VideoListDTO.class);
        List<VideoListDTO> dataHandles = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = videoService.importDataHandle(dataHandles, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 导出视频列表
     */
    @PreAuthorize("@ss.hasPermi('platform:video:export')")
    @Log(title = "视频", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VideoListDTO video) {
        List<VideoListVO> list = videoService.selectVideoListVO(video);
        ExcelUtil<VideoListVO> util = new ExcelUtil<VideoListVO>(VideoListVO.class);
        util.exportExcel(response, list, "视频数据");
    }
}
