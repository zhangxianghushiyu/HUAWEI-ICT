package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.VideoLike;
import com.ruoyi.platform.dto.VideoLikeListDTO;
import com.ruoyi.platform.vo.VideoLikeListVO;

/**
 * 视频点赞Service接口
 *
 * @author ruoyi
 * @date 2024-11-11
 */
public interface IVideoLikeService
{
    /**
     * 查询视频点赞
     *
     * @param id 视频点赞主键
     * @return 视频点赞
     */
    public VideoLike selectVideoLikeById(Long id);

    /**
     * 查询视频点赞列表
     *
     * @param videoLike 视频点赞
     * @return 视频点赞集合
     */
    public List<VideoLike> selectVideoLikeList(VideoLike videoLike);

    /**
     * 新增视频点赞
     *
     * @param videoLike 视频点赞
     * @return 结果
     */
    public int insertVideoLike(VideoLike videoLike);

    /**
     * 修改视频点赞
     *
     * @param videoLike 视频点赞
     * @return 结果
     */
    public int updateVideoLike(VideoLike videoLike);

    /**
     * 批量删除视频点赞
     *
     * @param ids 需要删除的视频点赞主键集合
     * @return 结果
     */
    public int deleteVideoLikeByIds(Long[] ids);

    /**
     * 删除视频点赞信息
     *
     * @param id 视频点赞主键
     * @return 结果
     */
    public int deleteVideoLikeById(Long id);

    /**
     * @param查询视频点赞列表
     * @return 视频点赞集合
     */
    List<VideoLikeListVO> selectVideoLikeListVO(VideoLikeListDTO videoLike);
}
