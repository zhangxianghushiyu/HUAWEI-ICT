package com.ruoyi.web.controller.platform;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.platform.domain.Drama;
import com.ruoyi.platform.dto.DramaDetailsListDTO;
import com.ruoyi.platform.service.IDramaService;
import com.ruoyi.platform.vo.DramaDetailsListVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.ruoyi.platform.domain.DramaDetails;
import com.ruoyi.platform.service.IDramaDetailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 短剧详细Controller
 *
 * @author ruoyi
 * @date 2024-11-10
 */
@RestController
@RequestMapping("/platform/dramaDetails")
public class DramaDetailsController extends BaseController {
    @Autowired
    private IDramaDetailsService dramaDetailsService;
    @Autowired
    private IDramaService dramaService;

    /**
     * 查询短剧详细列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaDetails:list')")
    @GetMapping("/list")
    public TableDataInfo list(DramaDetailsListDTO dramaDetails) {
        startPage();
        List<DramaDetailsListVO> list = dramaDetailsService.selectDramaDetailsListVO(dramaDetails);
        return getDataTable(list);
    }

    /**
     * 获取短剧详细详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaDetails:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(dramaDetailsService.selectDramaDetailsById(id));
    }

    /**
     * 新增短剧详细
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaDetails:add')")
    @Log(title = "短剧详细", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody DramaDetails dramaDetails) {
        //查看有没有已经存在的集数
        DramaDetails dramaDetails1 = dramaDetailsService.selectGather(dramaDetails.getDramaId().intValue(), dramaDetails.getGather());
        if (Objects.nonNull(dramaDetails1)) {
            return AjaxResult.error("已存在相同的集数");
        }
        //修改主信息的更新时间
        Drama drama = dramaService.selectDramaById(dramaDetails.getDramaId());
        //如果主信息是免费，那么这集不可以进行收费
        if (drama.getIsPrice()==0){
            if (dramaDetails.getIsPrice()==1){
                return AjaxResult.error("此部短剧设置的是免费，请将此集设置为免费。");
            }
        }
        drama.setUpTime(new Date());
        dramaService.updateDrama(drama);
        return toAjax(dramaDetailsService.insertDramaDetails(dramaDetails));
    }

    /**
     * 修改短剧详细
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaDetails:edit')")
    @Log(title = "短剧详细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DramaDetails dramaDetails) {
        //查看有没有已经存在的集数
        DramaDetails dramaDetails1 = dramaDetailsService.selectGather(dramaDetails.getDramaId().intValue(), dramaDetails.getGather());
        if (Objects.nonNull(dramaDetails1) && !dramaDetails.getId().equals(dramaDetails1.getId())) {
            return AjaxResult.error("已存在相同的集数");
        }
        Drama drama = dramaService.selectDramaById(dramaDetails.getDramaId());
        //如果主信息是免费，那么这集不可以进行收费
        if (drama.getIsPrice()==0){
            if (dramaDetails.getIsPrice()==1){
                return AjaxResult.error("此部短剧设置的是免费，请将此集设置为免费。");
            }
        }
        return toAjax(dramaDetailsService.updateDramaDetails(dramaDetails));
    }

    /**
     * 删除短剧详细
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaDetails:remove')")
    @Log(title = "短剧详细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dramaDetailsService.deleteDramaDetailsByIds(ids));
    }

    /**
     * 下架剧情
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaDetails:remove')")
    @Log(title = "剧情", businessType = BusinessType.DELETE)
    @DeleteMapping("/undercarriage/{ids}")
    public AjaxResult undercarriage(@PathVariable Long[] ids) {
        return toAjax(dramaDetailsService.undercarriage(ids));
    }

    /**
     * 上架
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaDetails:remove')")
    @Log(title = "剧情", businessType = BusinessType.DELETE)
    @DeleteMapping("/grounding/{ids}")
    public AjaxResult grounding(@PathVariable Long[] ids) {
        return toAjax(dramaDetailsService.grounding(ids));
    }


    /**
     * 批量解析视频宽高（补录历史数据）
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaDetails:edit')")
    @PostMapping("/refreshVideoSize")
    public AjaxResult refreshVideoSize() {
        dramaDetailsService.refreshVideoSize();
        return AjaxResult.success("已触发视频宽高解析");
    }


    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<DramaDetailsListVO> util = new ExcelUtil<DramaDetailsListVO>(DramaDetailsListVO.class);
        util.importTemplateExcel(response, "数据");
    }

    @Log(title = "数据管理", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, Long dramaId) throws Exception {
        ExcelUtil<DramaDetailsListDTO> util = new ExcelUtil<>(DramaDetailsListDTO.class);
        List<DramaDetailsListDTO> dataHandles = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = dramaDetailsService.importDataHandle(dataHandles, updateSupport,
                operName, dramaId);
        return AjaxResult.success(message);
    }


    /**
     * 导出短剧详细列表
     */
    @PreAuthorize("@ss.hasPermi('platform:dramaDetails:export')")
    @Log(title = "短剧详细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DramaDetailsListDTO dramaDetails) {
        List<DramaDetailsListVO> list = dramaDetailsService.selectDramaDetailsListVO(dramaDetails);
        ExcelUtil<DramaDetailsListVO> util = new ExcelUtil<DramaDetailsListVO>(DramaDetailsListVO.class);
        util.exportExcel(response, list, "短剧详细数据");
    }

}
