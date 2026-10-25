package com.ruoyi.web.controller.platform;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.dto.DramaWatchListDTO;
import com.ruoyi.platform.vo.DramaWatchListVO;
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
import com.ruoyi.platform.domain.DramaWatch;
import com.ruoyi.platform.service.IDramaWatchService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 观看记录Controller
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/platform/dramaWatch")
public class DramaWatchController extends BaseController
{
    @Autowired
    private IDramaWatchService dramaWatchService;

    /**
     * 查询观看记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaWatch:list')")
    @GetMapping("/list")
    public TableDataInfo list(DramaWatchListDTO dramaWatch)
    {
        startPage();
        List<DramaWatchListVO> list = dramaWatchService.selectDramaWatchListVO(dramaWatch);
        return getDataTable(list);
    }

    /**
     * 导出观看记录列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaWatch:export')")
    @Log(title = "观看记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DramaWatch dramaWatch)
    {
        List<DramaWatch> list = dramaWatchService.selectDramaWatchList(dramaWatch);
        ExcelUtil<DramaWatch> util = new ExcelUtil<DramaWatch>(DramaWatch.class);
        util.exportExcel(response, list, "观看记录数据");
    }

    /**
     * 获取观看记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaWatch:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(dramaWatchService.selectDramaWatchById(id));
    }

    /**
     * 新增观看记录
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaWatch:add')")
    @Log(title = "观看记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DramaWatch dramaWatch)
    {
        return toAjax(dramaWatchService.insertDramaWatch(dramaWatch));
    }

    /**
     * 修改观看记录
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaWatch:edit')")
    @Log(title = "观看记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DramaWatch dramaWatch)
    {
        return toAjax(dramaWatchService.updateDramaWatch(dramaWatch));
    }

    /**
     * 删除观看记录
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaWatch:remove')")
    @Log(title = "观看记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dramaWatchService.deleteDramaWatchByIds(ids));
    }
}
