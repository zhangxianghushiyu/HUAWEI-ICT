package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.MyDramaCollectVO;

import java.util.List;

public interface DramaCollectService {

    /**
     * 短剧收藏
     * @param dramaId
     */
    void collect(Integer dramaId);


    /**
     * 获得我的收藏
     * @return
     */
    List<MyDramaCollectVO> getMyDramaCollectVO();
}
