package com.ruoyi.web.controller.platform;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.dto.DramaCollectListDTO;
import com.ruoyi.platform.vo.DramaCollectListVO;
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
import com.ruoyi.platform.domain.DramaCollect;
import com.ruoyi.platform.service.IDramaCollectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短剧收藏Controller
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
@RestController
@RequestMapping("/platform/dramaCollect")
public class DramaCollectController extends BaseController
{
    @Autowired
    private IDramaCollectService dramaCollectService;

    /**
     * 查询短剧收藏列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCollect:list')")
    @GetMapping("/list")
    public TableDataInfo list(DramaCollectListDTO dramaCollect)
    {
        startPage();
        List<DramaCollectListVO> list = dramaCollectService.selectDramaCollectListVO(dramaCollect);
        return getDataTable(list);
    }

    /**
     * 导出短剧收藏列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCollect:export')")
    @Log(title = "短剧收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DramaCollect dramaCollect)
    {
        List<DramaCollect> list = dramaCollectService.selectDramaCollectList(dramaCollect);
        ExcelUtil<DramaCollect> util = new ExcelUtil<DramaCollect>(DramaCollect.class);
        util.exportExcel(response, list, "短剧收藏数据");
    }

    /**
     * 获取短剧收藏详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCollect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dramaCollectService.selectDramaCollectById(id));
    }

    /**
     * 新增短剧收藏
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCollect:add')")
    @Log(title = "短剧收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DramaCollect dramaCollect)
    {
        return toAjax(dramaCollectService.insertDramaCollect(dramaCollect));
    }

    /**
     * 修改短剧收藏
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCollect:edit')")
    @Log(title = "短剧收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DramaCollect dramaCollect)
    {
        return toAjax(dramaCollectService.updateDramaCollect(dramaCollect));
    }

    /**
     * 删除短剧收藏
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaCollect:remove')")
    @Log(title = "短剧收藏", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dramaCollectService.deleteDramaCollectByIds(ids));
    }
}
