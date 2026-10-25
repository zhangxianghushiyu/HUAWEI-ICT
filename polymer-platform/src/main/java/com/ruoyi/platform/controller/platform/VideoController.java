package com.ruoyi.platform.controller.platform;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.platform.config.annotation.RepeatSubmit;
import com.ruoyi.platform.service.VideoCollectService;
import com.ruoyi.platform.service.VideoLikeService;
import com.ruoyi.platform.service.VideoService;
import com.ruoyi.platform.utils.R;
import com.ruoyi.platform.vo.MyVideoCollectVO;
import com.ruoyi.platform.vo.MyVideoLikeVO;
import com.ruoyi.platform.vo.VideoListVO;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "视频信息API")
@RestController
@Slf4j
@RequestMapping("/platform/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private VideoLikeService videoLikeService;
    @Autowired
    private VideoCollectService videoCollectService;


    @ApiOperation("获取视频详情")
    @GetMapping("/getInfo")
    public R<VideoListVO> getVideoInfo(@RequestParam Integer id) {
        return R.ok(videoService.getVideoDetail(id));
    }
    @ApiOperation("获取视频列表")
    @GetMapping("/getList")
    public R<List<VideoListVO>> getVideoList() {
        List<VideoListVO> videoList = videoService.getVideoList();
        return R.ok(videoList);
    }

//    @ApiOperation("获取视频播放次数")
//    @GetMapping("/getPlayCount")
//    public R<Integer> updateVideoPlayCount(@RequestParam Integer id) {
//        return R.ok(videoService.updateVideoPlayCount(id));
//    }

    @ApiOperation(value = "视频点赞/取消")
    @ApiImplicitParam(name = "id", value = "视频ID", required = true)
    @GetMapping("/like")
    @RepeatSubmit(interval = 800)
    public R<Void> like(@RequestParam(value = "id") Integer id) {
        videoLikeService.like(id);
        return R.ok();
    }
    @ApiOperation(value = "视频收藏/取消")
    @ApiImplicitParam(name = "id", value = "视频ID", required = true)
    @GetMapping("/collection")
    @RepeatSubmit(interval = 800)
    public R<Void> collection(@RequestParam(value = "id") Integer id) {
        videoCollectService.collection(id);
        return R.ok();
    }

    @ApiOperation(value = "我的收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/myCollect")
    public R<PageInfo<MyVideoCollectVO>> myCollect(@RequestParam(value = "pageNum") Integer pageNum,
                                               @RequestParam(value = "pageSize") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<MyVideoCollectVO> myVideoCollectVO = videoCollectService.getMyVideoCollectVO();
        PageInfo<MyVideoCollectVO> myVideoCollectVOPageInfo = new PageInfo<>(myVideoCollectVO);
        return R.ok(myVideoCollectVOPageInfo);
    }

    @ApiOperation(value = "我的点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "起始页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = true)
    })
    @GetMapping("/myLike")
    public R<PageInfo<MyVideoLikeVO>> myLike(@RequestParam(value = "pageNum") Integer pageNum,
                                         @RequestParam(value = "pageSize") Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<MyVideoLikeVO> myVideoLikeVO = videoLikeService.getMyVideoLikeVO();
        PageInfo<MyVideoLikeVO> myVideoLikeVOPageInfo = new PageInfo<>(myVideoLikeVO);
        return R.ok(myVideoLikeVOPageInfo);
    }


    /**
     * 此接口已经废弃不使用
     * @param videoId
     * @return
     */
    @ApiOperation(value = "任务处理（查看某一个视频）")
    @ApiImplicitParam(name = "videoId", value = "视频ID", required = true)
    @GetMapping("/taskHandle")
    @RepeatSubmit
    public R<String> taskHandle(@RequestParam(value = "videoId") Integer videoId) {
        return R.ok();
    }
}
