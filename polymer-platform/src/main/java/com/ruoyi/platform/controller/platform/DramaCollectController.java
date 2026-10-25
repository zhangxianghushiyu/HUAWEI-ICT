package com.ruoyi.platform.controller.platform;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.service.DramaCollectService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.MyDramaCollectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "短剧收藏API")
@RestController
@Slf4j
@RequestMapping("/platform/dramaCollect")
public class DramaCollectController {

    @Autowired
    private DramaCollectService dramaCollectService;

    @ApiOperation(value = "短剧收藏/取消")
    @ApiImplicitParam(name = "dramaId", value = "短剧ID", required = true)
    @GetMapping("/collect")
    @RepeatSubmit(interval = 800)
    public R<Void> collect(@RequestParam(value = "dramaId") Integer dramaId) {
        dramaCollectService.collect(dramaId);
        return R.ok();
    }


    @ApiOperation(value = "我的收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/myCollect")
    public R<PageInfo<MyDramaCollectVO>> myCollect(@RequestParam(value = "pageNum") Integer pageNum,
                                               @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyDramaCollectVO> myDramaCollectVO = dramaCollectService.getMyDramaCollectVO();
        PageInfo<MyDramaCollectVO> myDramaCollectVOPageInfo = new PageInfo<>(myDramaCollectVO);
        return R.ok(myDramaCollectVOPageInfo);
    }
}
