package com.ruoyi.platform.controller.platform;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.service.DramaLookService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.MyDramaLookVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "短剧浏览API")
@RestController
@Slf4j
@RequestMapping("/platform/dramaLook")
public class DramaLookController {

    @Autowired
    private DramaLookService dramaLookService;

    @ApiOperation(value = "查看某一集短剧")
    @ApiImplicitParam(name = "dramaDetailsId", value = "短剧详细ID", required = true)
    @GetMapping("/info")
    public R<String> info(@RequestParam(value = "dramaDetailsId") Integer dramaDetailsId) {
        dramaLookService.look(dramaDetailsId);
        return R.ok();
    }


    @ApiOperation(value = "我的浏览记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/getRecord")
    public R<PageInfo<MyDramaLookVO>> getRecord(@RequestParam(value = "pageNum") Integer pageNum,
                                                @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyDramaLookVO> dramaLookRecordListVO = dramaLookService.getDramaLookRecordListVO();
        PageInfo<MyDramaLookVO> dramaLookRecordListVOPageInfo = new PageInfo<>(dramaLookRecordListVO);
        return R.ok(dramaLookRecordListVOPageInfo);
    }

    @ApiOperation(value = "删除浏览记录")
    @GetMapping("/remove")
    public R<Void> remove(@RequestParam(value = "id") Integer id) {
        dramaLookService.remove(id);
        return R.ok();
    }
}
