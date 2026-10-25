package com.ruoyi.web.controller.platform;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.platform.domain.GoodsOrder;
import com.ruoyi.platform.service.IGoodsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商城订单Controller
 * 
 * @author ruoyi
 * @date 2025-03-18
 */
@RestController
@RequestMapping("/platform/goodsOrder")
public class GoodsOrderController extends BaseController
{
    @Autowired
    private IGoodsOrderService goodsOrderService;

    /**
     * 查询商城订单列表
     */
    @PreAuthorize("@ss.hasPermi('platform:goodsOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsOrder goodsOrder)
    {
        startPage();
        List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
        return getDataTable(list);
    }

    /**
     * 导出商城订单列表
     */
    @PreAuthorize("@ss.hasPermi('platform:goodsOrder:export')")
    @Log(title = "商城订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsOrder goodsOrder)
    {
        List<GoodsOrder> list = goodsOrderService.selectGoodsOrderList(goodsOrder);
        ExcelUtil<GoodsOrder> util = new ExcelUtil<GoodsOrder>(GoodsOrder.class);
        util.exportExcel(response, list, "商城订单数据");
    }

    /**
     * 获取商城订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('platform:goodsOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(goodsOrderService.selectGoodsOrderById(id));
    }

    /**
     * 新增商城订单
     */
    @PreAuthorize("@ss.hasPermi('platform:goodsOrder:add')")
    @Log(title = "商城订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsOrder goodsOrder)
    {
        return toAjax(goodsOrderService.insertGoodsOrder(goodsOrder));
    }

    /**
     * 修改商城订单
     */
    @PreAuthorize("@ss.hasPermi('platform:goodsOrder:edit')")
    @Log(title = "商城订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsOrder goodsOrder)
    {
        return toAjax(goodsOrderService.updateGoodsOrder(goodsOrder));
    }

    /**
     * 删除商城订单
     */
    @PreAuthorize("@ss.hasPermi('platform:goodsOrder:remove')")
    @Log(title = "商城订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsOrderService.deleteGoodsOrderByIds(ids));
    }
}
