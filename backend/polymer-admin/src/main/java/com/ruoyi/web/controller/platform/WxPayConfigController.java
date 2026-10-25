package com.ruoyi.web.controller.platform;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.dto.AliPayConfigListDTO;
import com.ruoyi.platform.dto.WxPayConfigListDTO;
import com.ruoyi.platform.service.IParamsConfigService;
import com.ruoyi.platform.service.IWxPayConfigService;
import com.ruoyi.platform.vo.WxPayConfigListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 版本信息Controller
 *
 * @author ruoyi
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/platform/wxPayConfig")
public class WxPayConfigController extends BaseController {
    @Autowired
    private IWxPayConfigService wxPayConfigService;
    @Autowired
    private IParamsConfigService paramsConfigService;


    @GetMapping("/list")
    public TableDataInfo list(WxPayConfigListDTO dto) {
        startPage();
        List<WxPayConfigListVO> aliPayConfigListVO = wxPayConfigService.getWxPayConfigListVO(dto);
        return getDataTable(aliPayConfigListVO);
    }


    /**
     * 获取版本信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(wxPayConfigService.getParamsConfigInfo(id));
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
