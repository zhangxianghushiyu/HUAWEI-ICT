package com.ruoyi.web.controller.platform;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.dto.AliPayConfigListDTO;
import com.ruoyi.platform.service.IAliPayConfigService;
import com.ruoyi.platform.service.IParamsConfigService;
import com.ruoyi.platform.vo.AliPayConfigListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 版本信息Controller
 *
 * @author ruoyi
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/platform/aliPayConfig")
public class AliPayConfigController extends BaseController {
    @Autowired
    private IAliPayConfigService aliPayConfigService;
    @Autowired
    private IParamsConfigService paramsConfigService;


    @GetMapping("/list")
    public TableDataInfo list(AliPayConfigListDTO dto) {
        startPage();
        List<AliPayConfigListVO> aliPayConfigListVO = aliPayConfigService.getAliPayConfigListVO(dto);
        return getDataTable(aliPayConfigListVO);
    }


    /**
     * 获取版本信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(aliPayConfigService.getParamsConfigInfo(id));
    }


    /**
     * 修改版本信息
     */
    @PutMapping
    public AjaxResult edit(@RequestBody AliPayConfigListDTO dto) {
        ParamsConfig paramsConfig = paramsConfigService.selectParamsConfigById(dto.getId());
        paramsConfig.setValue(dto.getValue());
        return toAjax(paramsConfigService.updateParamsConfig(paramsConfig));
    }

}
