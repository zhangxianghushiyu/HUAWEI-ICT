package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.VideoLike;
import com.ruoyi.platform.dto.VideoLikeListDTO;
import com.ruoyi.platform.vo.VideoLikeListVO;

/**
 * 视频点赞Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-11
 */
public interface VideoLikeMapper
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
     * 删除视频点赞
     *
     * @param id 视频点赞主键
     * @return 结果
     */
    public int deleteVideoLikeById(Long id);

    /**
     * 批量删除视频点赞
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVideoLikeByIds(Long[] ids);

    /**
     * 查询视频点赞列表
     * @param videoLike
     * @return 视频点赞集合
     */
    List<VideoLikeListVO> selectVideoLikeListVO(VideoLikeListDTO videoLike);
}
