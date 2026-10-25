package com.ruoyi.platform.controller.platform;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.dto.GoodsGetDTO;
import com.ruoyi.platform.service.GoodsOrderService;
import com.ruoyi.platform.service.GoodsService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.GoodsListVO;
import com.ruoyi.platform.vo.GoodsOrderListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "商城信息API")
@RestController
@Slf4j
@RequestMapping("/platform/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsOrderService goodsOrderService;

    @ApiOperation("商品分类")
    @GetMapping("getGoodsTypeList")
    public R getGoodsTypeList(){
        return R.ok(goodsService.getGoodsTypeList());
    }

    @ApiOperation(value = "商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/list")
    public R<PageInfo<GoodsListVO>> list(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "goodsType", required = false) Integer goodsType,
            @RequestParam(value = "goodsName", required = false) String goodsName) {
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsListVO> goodsListVOS = goodsService.getGoodsListVO(goodsType, goodsName);
        PageInfo<GoodsListVO> goodsListVOPageInfo = new PageInfo<>(goodsListVOS);
        return R.ok(goodsListVOPageInfo);
    }


    @ApiOperation(value = "购买商品")
    @PostMapping("/buy")
    @RepeatSubmit
    public R<Void> buy(@Valid @RequestBody GoodsGetDTO dto) {
        goodsService.buy(dto);
        return R.ok();
    }


    @ApiOperation(value = "商品订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/goodsOrderList")
    public R<PageInfo<GoodsOrderListVO>> goodsOrderList(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsOrderListVO> goodsOrderListVO = goodsOrderService.getGoodsOrderListVO();
        PageInfo<GoodsOrderListVO> goodsListVOPageInfo = new PageInfo<>(goodsOrderListVO);
        return R.ok(goodsListVOPageInfo);
    }

    @ApiOperation(value = "确认收货")
    @ApiImplicitParam(name = "id", value = "订单ID", required = true)
    @GetMapping("/takeOver")
    public R<Void> takeOver(
            @RequestParam(value = "id") Integer id) {
        goodsOrderService.takeOver(id);
        return R.ok();
    }

}
