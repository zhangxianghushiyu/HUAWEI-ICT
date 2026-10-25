package com.ruoyi.web.controller.platform;

import java.util.List;
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
import com.ruoyi.platform.domain.ConvertRatio;
import com.ruoyi.platform.service.IConvertRatioService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 兑换比例Controller
 *
 * @author ruoyi
 * @date 2024-11-12
 */
@RestController
@RequestMapping("/platform/convertRatio")
public class ConvertRatioController extends BaseController {
    @Autowired
    private IConvertRatioService convertRatioService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询兑换比例列表
     */
    @PreAuthorize("@ss.hasPermi('platform:convertRatio:list')")
    @GetMapping("/list")
    public TableDataInfo list(ConvertRatio convertRatio) {
        startPage();
        List<ConvertRatio> list = convertRatioService.selectConvertRatioList(convertRatio);
        return getDataTable(list);
    }

    /**
     * 导出兑换比例列表
     */
    @PreAuthorize("@ss.hasPermi('platform:convertRatio:export')")
    @Log(title = "兑换比例", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ConvertRatio convertRatio) {
        List<ConvertRatio> list = convertRatioService.selectConvertRatioList(convertRatio);
        ExcelUtil<ConvertRatio> util = new ExcelUtil<ConvertRatio>(ConvertRatio.class);
        util.exportExcel(response, list, "兑换比例数据");
    }

    /**
     * 获取兑换比例详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:convertRatio:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(convertRatioService.selectConvertRatioById(id));
    }

    /**
     * 新增兑换比例
     */
    @PreAuthorize("@ss.hasPermi('platform:convertRatio:add')")
    @Log(title = "兑换比例", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConvertRatio convertRatio) {
        return toAjax(convertRatioService.insertConvertRatio(convertRatio));
    }

    /**
     * 修改兑换比例
     */
    @PreAuthorize("@ss.hasPermi('platform:convertRatio:edit')")
    @Log(title = "兑换比例", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConvertRatio convertRatio) {
        if (convertRatio.getRatio() < 10) {
            return AjaxResult.error("比例值最小为10");
        }
        if (convertRatio.getRatio() % 10 != 0) {//判断是否为10的倍数，即能被10整除且余数为0
            return AjaxResult.error("请输入10的倍数");
        }
        //redis删除数据
        redisCache.deleteObject(Constants.CONVERT_RATIO);
        return toAjax(convertRatioService.updateConvertRatio(convertRatio));
    }

    /**
     * 删除兑换比例
     */
    @PreAuthorize("@ss.hasPermi('platform:convertRatio:remove')")
    @Log(title = "兑换比例", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(convertRatioService.deleteConvertRatioByIds(ids));
    }
}
