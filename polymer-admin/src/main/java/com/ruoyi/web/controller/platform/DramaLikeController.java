package com.ruoyi.web.controller.platform;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.dto.DramaLikeListDTO;
import com.ruoyi.platform.vo.DramaLikeListVO;
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
import com.ruoyi.platform.domain.DramaLike;
import com.ruoyi.platform.service.IDramaLikeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短剧点赞Controller
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
@RestController
@RequestMapping("/platform/dramaLike")
public class DramaLikeController extends BaseController
{
    @Autowired
    private IDramaLikeService dramaLikeService;

    /**
     * 查询短剧点赞列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaLike:list')")
    @GetMapping("/list")
    public TableDataInfo list(DramaLikeListDTO dramaLike)
    {
        startPage();
        List<DramaLikeListVO> dramaLikeListVOS = dramaLikeService.selectDramaLikeListVO(dramaLike);
        return getDataTable(dramaLikeListVOS);
    }

    /**
     * 导出短剧点赞列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaLike:export')")
    @Log(title = "短剧点赞", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DramaLike dramaLike)
    {
        List<DramaLike> list = dramaLikeService.selectDramaLikeList(dramaLike);
        ExcelUtil<DramaLike> util = new ExcelUtil<DramaLike>(DramaLike.class);
        util.exportExcel(response, list, "短剧点赞数据");
    }

    /**
     * 获取短剧点赞详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaLike:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dramaLikeService.selectDramaLikeById(id));
    }

    /**
     * 新增短剧点赞
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaLike:add')")
    @Log(title = "短剧点赞", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DramaLike dramaLike)
    {
        return toAjax(dramaLikeService.insertDramaLike(dramaLike));
    }

    /**
     * 修改短剧点赞
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaLike:edit')")
    @Log(title = "短剧点赞", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DramaLike dramaLike)
    {
        return toAjax(dramaLikeService.updateDramaLike(dramaLike));
    }

    /**
     * 删除短剧点赞
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaLike:remove')")
    @Log(title = "短剧点赞", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dramaLikeService.deleteDramaLikeByIds(ids));
    }
}
