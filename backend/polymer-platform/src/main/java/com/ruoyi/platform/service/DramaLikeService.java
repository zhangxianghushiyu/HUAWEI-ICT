package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.MyDramaLikeVO;

import java.util.List;

public interface DramaLikeService {

    /**
     * 短剧点赞
     */
    void like(Integer dramaDetailsId);

    /**
     * 查询我的点赞
     * @return
     */
    List<MyDramaLikeVO> getMyDramaLike();
}
