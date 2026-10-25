package com.ruoyi.web.controller.platform;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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
import com.ruoyi.platform.domain.TreasureBox;
import com.ruoyi.platform.service.ITreasureBoxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 宝箱配置Controller
 *
 * @author ruoyi
 * @date 2024-12-02
 */
@RestController
@RequestMapping("/platform/treasureBox")
public class TreasureBoxController extends BaseController {
    @Autowired
    private ITreasureBoxService treasureBoxService;

    /**
     * 查询宝箱配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:treasureBox:list')")
    @GetMapping("/list")
    public TableDataInfo list(TreasureBox treasureBox) {
        startPage();
        List<TreasureBox> list = treasureBoxService.selectTreasureBoxList(treasureBox);
        return getDataTable(list);
    }

    /**
     * 导出宝箱配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:treasureBox:export')")
    @Log(title = "宝箱配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TreasureBox treasureBox) {
        List<TreasureBox> list = treasureBoxService.selectTreasureBoxList(treasureBox);
        ExcelUtil<TreasureBox> util = new ExcelUtil<TreasureBox>(TreasureBox.class);
        util.exportExcel(response, list, "宝箱配置数据");
    }

    /**
     * 获取宝箱配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:treasureBox:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(treasureBoxService.selectTreasureBoxById(id));
    }

    /**
     * 新增宝箱配置
     */
    @PreAuthorize("@ss.hasPermi('platform:treasureBox:add')")
    @Log(title = "宝箱配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TreasureBox treasureBox) {
        return toAjax(treasureBoxService.insertTreasureBox(treasureBox));
    }

    /**
     * 修改宝箱配置
     */
    @PreAuthorize("@ss.hasPermi('platform:treasureBox:edit')")
    @Log(title = "宝箱配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TreasureBox treasureBox) {
        if (treasureBox.getMinute() <= 0) {
            return AjaxResult.error("分钟最小为1");
        }
        if (Objects.isNull(treasureBox.getPrice()) || treasureBox.getPrice().compareTo(new BigDecimal(0)) <= 0) {
            return AjaxResult.error("金币必须大于0");
        }
        return toAjax(treasureBoxService.updateTreasureBox(treasureBox));
    }

    /**
     * 删除宝箱配置
     */
    @PreAuthorize("@ss.hasPermi('platform:treasureBox:remove')")
    @Log(title = "宝箱配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(treasureBoxService.deleteTreasureBoxByIds(ids));
    }
}
