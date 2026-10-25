package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.VideoCategoryVO;
import com.ruoyi.platform.vo.VideoSeriesEpisodeVO;
import com.ruoyi.platform.vo.VideoSeriesVO;

import java.util.List;

public interface VideoSeriesService {

    /**
     * 视频分类列表
     */
    List<VideoCategoryVO> getCategoryList();

    /**
     * 视频系列列表
     */
    List<VideoSeriesVO> getSeriesList(Integer categoryId);

    /**
     * 按视频名称模糊搜索系列
     */
    List<VideoSeriesVO> searchSeries(String name);

    /**
     * 视频系列选集列表
     */
    List<VideoSeriesEpisodeVO> getEpisodeList(String seriesName);

    /**
     * 随机 Feed：每个系列随机取一集，结果随机排序（配合 PageHelper 分页）
     */
    List<VideoSeriesEpisodeVO> getRandomFeed();

    /**
     * 金币解锁视频单集
     */
    void moneyUnlock(Integer videoId);
}
