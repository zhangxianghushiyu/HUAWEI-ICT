package com.ruoyi.web.controller.platform;

import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
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
import com.ruoyi.platform.domain.OfficialCode;
import com.ruoyi.platform.service.IOfficialCodeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 官方二维码信息Controller
 *
 * @author ruoyi
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/platform/officialCode")
public class OfficialCodeController extends BaseController {
    @Autowired
    private IOfficialCodeService officialCodeService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询官方二维码信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:officialCode:list')")
    @GetMapping("/list")
    public TableDataInfo list(OfficialCode officialCode) {
        startPage();
        List<OfficialCode> list = officialCodeService.selectOfficialCodeList(officialCode);
        return getDataTable(list);
    }

    /**
     * 导出官方二维码信息列表
     */
    @PreAuthorize("@ss.hasPermi('platform:officialCode:export')")
    @Log(title = "官方二维码信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OfficialCode officialCode) {
        List<OfficialCode> list = officialCodeService.selectOfficialCodeList(officialCode);
        ExcelUtil<OfficialCode> util = new ExcelUtil<OfficialCode>(OfficialCode.class);
        util.exportExcel(response, list, "官方二维码信息数据");
    }

    /**
     * 获取官方二维码信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:officialCode:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(officialCodeService.selectOfficialCodeById(id));
    }

    /**
     * 新增官方二维码信息
     */
    @PreAuthorize("@ss.hasPermi('platform:officialCode:add')")
    @Log(title = "官方二维码信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OfficialCode officialCode) {
        //查看是不是已经存在二维码
        OfficialCode officialCodeInfo = officialCodeService.getOfficialCodeInfo();
        if (Objects.nonNull(officialCodeInfo)) {
            return AjaxResult.error("已经存在信息，无法新增");
        }
        return toAjax(officialCodeService.insertOfficialCode(officialCode));
    }

    /**
     * 修改官方二维码信息
     */
    @PreAuthorize("@ss.hasPermi('platform:officialCode:edit')")
    @Log(title = "官方二维码信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OfficialCode officialCode) {
        redisCache.deleteObject(Constants.OFFICIAL_CODE);
        return toAjax(officialCodeService.updateOfficialCode(officialCode));
    }

    /**
     * 删除官方二维码信息
     */
    @PreAuthorize("@ss.hasPermi('platform:officialCode:remove')")
    @Log(title = "官方二维码信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(officialCodeService.deleteOfficialCodeByIds(ids));
    }
}
