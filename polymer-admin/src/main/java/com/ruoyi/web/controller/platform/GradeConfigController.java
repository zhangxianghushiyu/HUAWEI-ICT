package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.GradeConfig;
import com.ruoyi.platform.service.IGradeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 用户阶级配置Controller
 *
 * @author ruoyi
 * @date 2025-02-10
 */
@RestController
@RequestMapping("/platform/gradeConfig")
public class GradeConfigController extends BaseController {
    @Autowired
    private IGradeConfigService gradeConfigService;

    /**
     * 查询用户阶级配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:gradeConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(GradeConfig gradeConfig) {
        startPage();
        List<GradeConfig> list = gradeConfigService.selectGradeConfigList(gradeConfig);
        return getDataTable(list);
    }

    /**
     * 导出用户阶级配置列表
     */
    @PreAuthorize("@ss.hasPermi('platform:gradeConfig:export')")
    @Log(title = "用户阶级配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GradeConfig gradeConfig) {
        List<GradeConfig> list = gradeConfigService.selectGradeConfigList(gradeConfig);
        ExcelUtil<GradeConfig> util = new ExcelUtil<GradeConfig>(GradeConfig.class);
        util.exportExcel(response, list, "用户阶级配置数据");
    }

    /**
     * 获取用户阶级配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:gradeConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(gradeConfigService.selectGradeConfigById(id));
    }

    /**
     * 新增用户阶级配置
     */
    @PreAuthorize("@ss.hasPermi('platform:gradeConfig:add')")
    @Log(title = "用户阶级配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GradeConfig gradeConfig) {
        GradeConfig gradeConfig1 = gradeConfigService.selectGradeConfigByGrade(gradeConfig.getGrade().intValue());
        if (Objects.nonNull(gradeConfig1)) {
            return AjaxResult.error("已存在相同的阶级配置");
        }
        return toAjax(gradeConfigService.insertGradeConfig(gradeConfig));
    }

    /**
     * 修改用户阶级配置
     */
    @PreAuthorize("@ss.hasPermi('platform:gradeConfig:edit')")
    @Log(title = "用户阶级配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GradeConfig gradeConfig) {
        GradeConfig gradeConfig1 = gradeConfigService.selectGradeConfigByGrade(gradeConfig.getGrade().intValue());
        if (Objects.nonNull(gradeConfig1) && !gradeConfig1.getId().equals(gradeConfig.getId())) {
            return AjaxResult.error("已存在相同的阶级配置");
        }
        return toAjax(gradeConfigService.updateGradeConfig(gradeConfig));
    }

    /**
     * 删除用户阶级配置
     */
    @PreAuthorize("@ss.hasPermi('platform:gradeConfig:remove')")
    @Log(title = "用户阶级配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(gradeConfigService.deleteGradeConfigByIds(ids));
    }
}
