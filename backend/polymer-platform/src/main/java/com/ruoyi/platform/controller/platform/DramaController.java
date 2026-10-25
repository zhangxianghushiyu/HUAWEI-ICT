package com.ruoyi.platform.controller.platform;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.service.*;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "短剧信息API")
@RestController
@Slf4j
@RequestMapping("/platform/drama")
public class DramaController {

    @Autowired
    private DramaCategoryService dramaCategoryService;
    @Autowired
    private DramaService dramaService;
    @Autowired
    private DramaDetailsService dramaDetailsService;

    @ApiOperation(value = "短剧分类")
    @GetMapping("/getCategory")
    public R<List<DramaCategoryVO>> getCategory() {
        return R.ok(dramaCategoryService.getDramaCategoryVO());
    }


    @ApiOperation(value = "首页短剧信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mainType", value = "主类型（1：精选，2：免费，3：新剧）", required = true),
            @ApiImplicitParam(name = "categoryId", value = "副分类ID"),
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/homePage")
    public R<PageInfo<DramaHomePageVO>> homePage(@RequestParam(value = "mainType") Integer mainType,
                                                 @RequestParam(value = "categoryId",required = false) Integer categoryId,
                                                 @RequestParam(value = "pageNum") Integer pageNum,
                                                 @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DramaHomePageVO> dramaHomePageVOS = dramaService.homePage(mainType,categoryId);
        PageInfo<DramaHomePageVO> dramaHomePageVOPageInfo = new PageInfo<>(dramaHomePageVOS);
        return R.ok(dramaHomePageVOPageInfo);
    }


    @ApiOperation(value = "短剧搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "标题", required = true),
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/query")
    public R<PageInfo<DramaHomePageVO>> query(
                                                 @RequestParam(value = "title") String title,
                                                 @RequestParam(value = "pageNum") Integer pageNum,
                                                 @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DramaHomePageVO> dramaHomePageVOS = dramaService.query(title);
        PageInfo<DramaHomePageVO> dramaHomePageVOPageInfo = new PageInfo<>(dramaHomePageVOS);
        return R.ok(dramaHomePageVOPageInfo);
    }


    @ApiOperation(value = "短剧详细信息集合")
            @ApiImplicitParam(name = "dramaId", value = "短剧ID", required = true)
    @GetMapping("/getDetailsList")
    public R<List<DramaDetailsListVO>> getDetailsList(@RequestParam(value = "dramaId") Integer dramaId) {
        List<DramaDetailsListVO> dramaDetailsListVO = dramaDetailsService.getDramaDetailsListVO(dramaId);
        return R.ok(dramaDetailsListVO);
    }


    @ApiOperation(value = "金币解锁短剧")
    @ApiImplicitParam(name = "dramaId", value = "短剧ID", required = true)
    @GetMapping("/moneyUnlock")
    @RepeatSubmit(interval = 1500)
    public R<Void> moneyUnlock(@RequestParam(value = "dramaId") Integer dramaId) {
        dramaService.moneyUnlock(dramaId);
        return R.ok();
    }


//    @ApiOperation(value = "短剧加播放量")
//    @ApiImplicitParam(name = "dramaDetailsId", value = "短剧详细ID", required = true)
//    @GetMapping("/addDramaCount")
//    public R<Void> addPlayCount(@RequestParam(value = "dramaDetailsId") Integer dramaDetailsId) {
//        dramaService.addDramaPlayCount(dramaDetailsId);
//        return R.ok();
//    }
}
