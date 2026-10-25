package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.Barrier;
import com.ruoyi.platform.service.IBarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 关卡信息Controller
 *
 * @author ruoyi
 * @date 2025-02-07
 */
@RestController
@RequestMapping("/platform/barrier")
public class BarrierController extends BaseController {
    @Autowired
    private IBarrierService barrierService;

    /**
     * 查询关卡信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:barrier:list')")
    @GetMapping("/list")
    public TableDataInfo list(Barrier barrier) {
        startPage();
        List<Barrier> list = barrierService.selectBarrierList(barrier);
        return getDataTable(list);
    }

    /**
     * 导出关卡信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:barrier:export')")
    @Log(title = "关卡信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Barrier barrier) {
        List<Barrier> list = barrierService.selectBarrierList(barrier);
        ExcelUtil<Barrier> util = new ExcelUtil<Barrier>(Barrier.class);
        util.exportExcel(response, list, "关卡信息数据");
    }

    /**
     * 获取关卡信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:barrier:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(barrierService.selectBarrierById(id));
    }

    /**
     * 新增关卡信息
     */
    @PreAuthorize("@ss.hasPermi('platform:barrier:add')")
    @Log(title = "关卡信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Barrier barrier) {
        Barrier barrier1 = barrierService.selectBarrierByBarrier(barrier.getBarrier());
        if (Objects.nonNull(barrier1)) {
            return AjaxResult.error("已存在相同的关卡");
        }
        return toAjax(barrierService.insertBarrier(barrier));
    }

    /**
     * 修改关卡信息
     */
    @PreAuthorize("@ss.hasPermi('platform:barrier:edit')")
    @Log(title = "关卡信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Barrier barrier) {
        Barrier barrier1 = barrierService.selectBarrierByBarrier(barrier.getBarrier());
        if (Objects.nonNull(barrier1) && !barrier1.getId().equals(barrier.getId())) {
            return AjaxResult.error("已存在相同的关卡");
        }
        return toAjax(barrierService.updateBarrier(barrier));
    }

    /**
     * 删除关卡信息
     */
    @PreAuthorize("@ss.hasPermi('platform:barrier:remove')")
    @Log(title = "关卡信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(barrierService.deleteBarrierByIds(ids));
    }
}
