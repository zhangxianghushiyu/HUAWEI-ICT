package com.ruoyi.web.controller.platform;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.platform.domain.ParamsConfig;
import com.ruoyi.platform.dto.ClientConfigListDTO;
import com.ruoyi.platform.service.IParamsConfigService;
import com.ruoyi.platform.vo.ClientConfigListVO;
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
@RequestMapping("/platform/clientConfig")
public class ClientConfigController extends BaseController {
    @Autowired
    private IParamsConfigService paramsConfigService;


    @GetMapping("/list")
    public TableDataInfo list(ClientConfigListDTO dto) {
        startPage();
        List<ClientConfigListVO> aliPayConfigListVO = paramsConfigService.getClientConfigListVO(dto);
        return getDataTable(aliPayConfigListVO);
    }


    /**
     * 获取详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(paramsConfigService.selectParamsConfigById(id));
    }


    /**
     * 修改信息
     */
    @PutMapping
    public AjaxResult edit(@RequestBody ClientConfigListDTO dto) {
        ParamsConfig paramsConfig = paramsConfigService.selectParamsConfigById(dto.getId());
        paramsConfig.setValue(dto.getValue());
        return toAjax(paramsConfigService.updateParamsConfig(paramsConfig));
    }

}
