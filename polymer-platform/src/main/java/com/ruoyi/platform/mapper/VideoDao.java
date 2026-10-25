package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.Video;
import com.ruoyi.platform.vo.VideoListVO;
import com.ruoyi.platform.vo.VideoSeriesEpisodeVO;
import com.ruoyi.platform.vo.VideoSeriesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoDao {

    Video selectByPrimaryKey(Integer id);


    List<VideoListVO> getVideoList();

    Integer updateVideoPlayCount(Integer id);

    /**
     * 添加短视频点赞量
     */
    Integer addVideoLikeCount(@Param("id") Integer id);

    /**
     * 减去短视频点赞量
     */
    Integer subVideoLikeCount(@Param("id") Integer id);

    /**
     * 添加短视频收藏量
     */
    Integer addVideoCollectCount(@Param("id") Integer id);

    /**
     * 减去短视频收藏量
     */
    Integer subVideoCollectCount(@Param("id") Integer id);

    /**
     * 视频系列列表（按系列名分组，可按分类筛选）
     */
    List<VideoSeriesVO> selectSeriesList(@Param("categoryId") Integer categoryId);

    /**
     * 按视频名称模糊搜索系列（系列名或系列内视频名命中）
     */
    List<VideoSeriesVO> selectSeriesListByName(@Param("name") String name);

    /**
     * 视频系列选集列表（按集数排序）
     */
    List<VideoSeriesEpisodeVO> selectEpisodeList(@Param("seriesName") String seriesName);

    /**
     * 看广告解锁视频系列：从指定视频所在集开始，向后取未解锁的前N集ID
     */
    List<Integer> selectNeedUnlockVideoIds(@Param("userId") Integer userId,
                                           @Param("videoId") Integer videoId,
                                           @Param("limit") Integer limit);

    /**
     * 随机 Feed：每个系列随机取一集，结果随机排序（配合 PageHelper 分页）
     */
    List<VideoSeriesEpisodeVO> selectRandomFeed();
}
