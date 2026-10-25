package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.UserStatusEnum;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.Detection;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.service.IDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 风控管理Controller
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
@RestController
@RequestMapping("/platform/detection")
public class DetectionController extends BaseController
{
    @Autowired
    private IDetectionService detectionService;

    /**
     * 查询风控管理列表
     */
    @PreAuthorize("@ss.hasPermi('platform:detection:list')")
    @GetMapping("/list")
    public TableDataInfo list(Detection detection)
    {
        startPage();
        List<Detection> list = detectionService.selectDetectionList(detection);
        return getDataTable(list);
    }

    /**
     * 导出风控管理列表
     */
    @PreAuthorize("@ss.hasPermi('platform:detection:export')")
    @Log(title = "风控管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Detection detection)
    {
        List<Detection> list = detectionService.selectDetectionList(detection);
        ExcelUtil<Detection> util = new ExcelUtil<Detection>(Detection.class);
        util.exportExcel(response, list, "风控管理数据");
    }

    /**
     * 获取风控管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:detection:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(detectionService.selectDetectionById(id));
    }

    /**
     * 新增风控管理
     */
    @PreAuthorize("@ss.hasPermi('platform:detection:add')")
    @Log(title = "风控管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Detection detection)
    {
        return toAjax(detectionService.insertDetection(detection));
    }

    /**
     * 修改风控管理
     */
    @PreAuthorize("@ss.hasPermi('platform:detection:edit')")
    @Log(title = "风控管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    public AjaxResult edit(@RequestBody Detection detection)
    {
        int i = detectionService.updateDetection(detection);
        if (detection.getStatus()==0){
            detectionService.updateValueIsNull(detection.getId());
        }
        return toAjax(i);
    }

    /**
     * 删除风控管理
     */
    @PreAuthorize("@ss.hasPermi('platform:detection:remove')")
    @Log(title = "风控管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(detectionService.deleteDetectionByIds(ids));
    }

    /**
     */
    @GetMapping("/modifyStatusDeactivate")
    public AjaxResult modifyStatusDeactivate(Long id) {
        Detection detection = detectionService.selectDetectionById(id);
        if (Objects.nonNull(detection)) {
            detection.setStatus(detection.getStatus().equals(UserStatusEnum.STATUS_0.getStatus()) ?
                    UserStatusEnum.STATUS_1.getStatus() : UserStatusEnum.STATUS_0.getStatus());
            detectionService.updateDetection(detection);
        }
        return AjaxResult.success();
    }
}
