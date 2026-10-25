package com.ruoyi.web.controller.platform;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.dto.AliInfoConfigListDTO;
import com.ruoyi.platform.service.IAliInfoConfigService;
import com.ruoyi.platform.service.IParamsConfigService;
import com.ruoyi.platform.vo.AliInfoConfigListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 阿里信息Controller
 *
 * @author ruoyi
 * @date 2024-11-17
 */
@RestController
@RequestMapping("/platform/aliInfoConfig")
public class AliInfoConfigController extends BaseController {
    @Autowired
    private IAliInfoConfigService aliInfoConfigService;
    @Autowired
    private IParamsConfigService paramsConfigService;


    @GetMapping("/list")
    public TableDataInfo list(AliInfoConfigListDTO dto) {
        startPage();
        List<AliInfoConfigListVO> aliInfoConfigListVOS = aliInfoConfigService.getAliInfoConfigListVO(dto);
        return getDataTable(aliInfoConfigListVOS);
    }


    /**
     * 获取信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(aliInfoConfigService.getParamsConfigInfo(id));
    }


    /**
     * 修改信息
     */
    @PutMapping
    public AjaxResult edit(@RequestBody AliInfoConfigListDTO dto) {
        ParamsConfig paramsConfig = paramsConfigService.selectParamsConfigById(dto.getId());
        paramsConfig.setValue(dto.getValue());
        return toAjax(paramsConfigService.updateParamsConfig(paramsConfig));
    }

}
