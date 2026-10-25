package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.VideoListVO;

import java.util.List;

public interface VideoService {


    /**
     * 获取视频信息
     */
    VideoListVO getVideoDetail(Integer id);

    /**
     * 获取视频列表
     */
    List<VideoListVO> getVideoList();

    /**
     * 获取视频播放次数
     *
     * @return
     */
    Integer updateVideoPlayCount(Integer id);

}
