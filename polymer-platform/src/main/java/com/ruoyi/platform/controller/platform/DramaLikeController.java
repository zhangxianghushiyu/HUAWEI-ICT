package com.ruoyi.platform.controller.platform;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.service.*;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.MyDramaLikeVO;
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


@Api(tags = "短剧点赞API")
@RestController
@Slf4j
@RequestMapping("/platform/dramaLike")
public class DramaLikeController {

    @Autowired
    private DramaLikeService dramaLikeService;

    @ApiOperation(value = "短剧点赞/取消")
    @ApiImplicitParam(name = "dramaDetailsId", value = "短剧详细ID", required = true)
    @GetMapping("/like")
    @RepeatSubmit(interval = 800)
    public R<Void> like(@RequestParam(value = "dramaDetailsId") Integer dramaDetailsId) {
        dramaLikeService.like(dramaDetailsId);
        return R.ok();
    }

    @ApiOperation(value = "我的点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/myLike")
    public R<PageInfo<MyDramaLikeVO>> myLike(@RequestParam(value = "pageNum") Integer pageNum,
                                                   @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyDramaLikeVO> myDramaLike = dramaLikeService.getMyDramaLike();
        PageInfo<MyDramaLikeVO> myDramaLikeVOPageInfo = new PageInfo<>(myDramaLike);
        return R.ok(myDramaLikeVOPageInfo);
    }
}
