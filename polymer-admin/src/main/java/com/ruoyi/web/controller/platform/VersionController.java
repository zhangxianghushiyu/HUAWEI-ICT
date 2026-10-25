package com.ruoyi.web.controller.platform;

import java.util.Date;
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
import com.ruoyi.platform.domain.Version;
import com.ruoyi.platform.service.IVersionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 版本信息Controller
 * 
 * @author ruoyi
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/platform/version")
public class VersionController extends BaseController
{
    @Autowired
    private IVersionService versionService;

    /**
     * 查询版本信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:version:list')")
    @GetMapping("/list")
    public TableDataInfo list(Version version)
    {
        startPage();
        List<Version> list = versionService.selectVersionList(version);
        return getDataTable(list);
    }

    /**
     * 导出版本信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:version:export')")
    @Log(title = "版本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Version version)
    {
        List<Version> list = versionService.selectVersionList(version);
        ExcelUtil<Version> util = new ExcelUtil<Version>(Version.class);
        util.exportExcel(response, list, "版本信息数据");
    }

    /**
     * 获取版本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:version:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(versionService.selectVersionById(id));
    }

    /**
     * 新增版本信息
     */
    @PreAuthorize("@ss.hasPermi('platform:version:add')")
    @Log(title = "版本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Version version)
    {
        version.setCreateTime(new Date());
        return toAjax(versionService.insertVersion(version));
    }

    /**
     * 修改版本信息
     */
    @PreAuthorize("@ss.hasPermi('platform:version:edit')")
    @Log(title = "版本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Version version)
    {
        version.setCreateTime(new Date());
        return toAjax(versionService.updateVersion(version));
    }

    /**
     * 删除版本信息
     */
    @PreAuthorize("@ss.hasPermi('platform:version:remove')")
    @Log(title = "版本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(versionService.deleteVersionByIds(ids));
    }
}
