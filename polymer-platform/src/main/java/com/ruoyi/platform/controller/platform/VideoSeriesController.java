package com.ruoyi.platform.controller.platform;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.service.VideoSeriesService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.VideoCategoryVO;
import com.ruoyi.platform.vo.VideoSeriesEpisodeVO;
import com.ruoyi.platform.vo.VideoSeriesVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "视频系列API")
@RestController
@Slf4j
@RequestMapping("/platform/videoSeries")
public class VideoSeriesController {

    @Autowired
    private VideoSeriesService videoSeriesService;

    @ApiOperation(value = "视频分类列表")
    @GetMapping("/categories")
    public R<List<VideoCategoryVO>> categories() {
        return R.ok(videoSeriesService.getCategoryList());
    }

    @ApiOperation(value = "视频系列列表")
    @GetMapping("/list")
    public R<PageInfo<VideoSeriesVO>> list(@RequestParam(value = "pageNum") Integer pageNum,
                                           @RequestParam(value = "pageSize") Integer pageSize,
                                           @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        PageHelper.startPage(pageNum, pageSize);
        List<VideoSeriesVO> list = videoSeriesService.getSeriesList(categoryId);
        return R.ok(new PageInfo<>(list));
    }

    @ApiOperation(value = "按视频名称模糊搜索系列")
    @ApiImplicitParam(name = "name", value = "视频名称关键字", required = true)
    @GetMapping("/search")
    public R<List<VideoSeriesVO>> search(@RequestParam(value = "name") String name) {
        return R.ok(videoSeriesService.searchSeries(name));
    }

    @ApiOperation(value = "视频系列选集列表")
    @ApiImplicitParam(name = "seriesName", value = "系列名", required = true)
    @GetMapping("/episodes")
    public R<List<VideoSeriesEpisodeVO>> episodes(@RequestParam(value = "seriesName") String seriesName) {
        return R.ok(videoSeriesService.getEpisodeList(seriesName));
    }

    @ApiOperation(value = "随机 Feed：每个系列随机取一集，结果随机排序")
    @GetMapping("/randomFeed")
    public R<PageInfo<VideoSeriesEpisodeVO>> randomFeed(
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<VideoSeriesEpisodeVO> list = videoSeriesService.getRandomFeed();
        return R.ok(new PageInfo<>(list));
    }

    @ApiOperation(value = "金币解锁视频单集")
    @ApiImplicitParam(name = "videoId", value = "视频ID", required = true)
    @GetMapping("/moneyUnlock")
    @RepeatSubmit(interval = 1500)
    public R<Void> moneyUnlock(@RequestParam(value = "videoId") Integer videoId) {
        videoSeriesService.moneyUnlock(videoId);
        return R.ok();
    }
}
