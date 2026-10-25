package com.ruoyi.web.controller.platform;

import java.math.BigDecimal;
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
import com.ruoyi.platform.domain.DramaUnlock;
import com.ruoyi.platform.service.IDramaUnlockService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 短剧解锁配置Controller
 *
 * @author ruoyi
 * @date 2025-01-17
 */
@RestController
@RequestMapping("/platform/dramaUnlock")
public class DramaUnlockController extends BaseController {
    @Autowired
    private IDramaUnlockService dramaUnlockService;

    /**
     * 查询短剧解锁配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaUnlock:list')")
    @GetMapping("/list")
    public TableDataInfo list(DramaUnlock dramaUnlock) {
        startPage();
        List<DramaUnlock> list = dramaUnlockService.selectDramaUnlockList(dramaUnlock);
        return getDataTable(list);
    }

    /**
     * 导出短剧解锁配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaUnlock:export')")
    @Log(title = "短剧解锁配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DramaUnlock dramaUnlock) {
        List<DramaUnlock> list = dramaUnlockService.selectDramaUnlockList(dramaUnlock);
        ExcelUtil<DramaUnlock> util = new ExcelUtil<DramaUnlock>(DramaUnlock.class);
        util.exportExcel(response, list, "短剧解锁配置数据");
    }

    /**
     * 获取短剧解锁配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaUnlock:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(dramaUnlockService.selectDramaUnlockById(id));
    }

    /**
     * 新增短剧解锁配置
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaUnlock:add')")
    @Log(title = "短剧解锁配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DramaUnlock dramaUnlock) {
        return toAjax(dramaUnlockService.insertDramaUnlock(dramaUnlock));
    }

    /**
     * 修改短剧解锁配置
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaUnlock:edit')")
    @Log(title = "短剧解锁配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DramaUnlock dramaUnlock) {
        if (dramaUnlock.getFreeNum() < 0) {
            return AjaxResult.error("免费集数不能小于0");
        }
        if (dramaUnlock.getUnlockNum() <= 0) {
            return AjaxResult.error("解锁集数必须大于0");
        }
        if (dramaUnlock.getMoney().compareTo(BigDecimal.ZERO) < 1) {
            return AjaxResult.error("消耗金币必须大于0");
        }
        return toAjax(dramaUnlockService.updateDramaUnlock(dramaUnlock));
    }

    /**
     * 删除短剧解锁配置
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaUnlock:remove')")
    @Log(title = "短剧解锁配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dramaUnlockService.deleteDramaUnlockByIds(ids));
    }
}
