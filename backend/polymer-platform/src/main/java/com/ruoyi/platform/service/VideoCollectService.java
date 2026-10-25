package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.MyVideoCollectVO;

import java.util.List;

public interface VideoCollectService {
    /**
     * 视频收藏
     */
    void collection(Integer id);


    /**
     * 我的收藏
     */
    List<MyVideoCollectVO> getMyVideoCollectVO();
}
