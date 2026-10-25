package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.MyVideoLikeVO;

import java.util.List;

public interface VideoLikeService {

    /**
     * 视频点赞
     */
    void like(Integer id);

    /**
     * 我的喜欢
     */
    List<MyVideoLikeVO> getMyVideoLikeVO();
}
