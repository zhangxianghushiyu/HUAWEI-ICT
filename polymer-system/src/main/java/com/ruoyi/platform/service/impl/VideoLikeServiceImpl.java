package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.dto.VideoLikeListDTO;
import com.ruoyi.platform.vo.VideoLikeListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.VideoLikeMapper;
import com.ruoyi.platform.domain.VideoLike;
import com.ruoyi.platform.service.IVideoLikeService;

/**
 * 视频点赞Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-11
 */
@Service
public class VideoLikeServiceImpl implements IVideoLikeService
{
    @Autowired
    private VideoLikeMapper videoLikeMapper;

    /**
     * 查询视频点赞
     *
     * @param id 视频点赞主键
     * @return 视频点赞
     */
    @Override
    public VideoLike selectVideoLikeById(Long id)
    {
        return videoLikeMapper.selectVideoLikeById(id);
    }

    /**
     * 查询视频点赞列表
     *
     * @param videoLike 视频点赞
     * @return 视频点赞
     */
    @Override
    public List<VideoLike> selectVideoLikeList(VideoLike videoLike)
    {
        return videoLikeMapper.selectVideoLikeList(videoLike);
    }

    /**
     * 新增视频点赞
     *
     * @param videoLike 视频点赞
     * @return 结果
     */
    @Override
    public int insertVideoLike(VideoLike videoLike)
    {
        videoLike.setCreateTime(DateUtils.getNowDate());
        return videoLikeMapper.insertVideoLike(videoLike);
    }

    /**
     * 修改视频点赞
     *
     * @param videoLike 视频点赞
     * @return 结果
     */
    @Override
    public int updateVideoLike(VideoLike videoLike)
    {
        videoLike.setUpdateTime(DateUtils.getNowDate());
        return videoLikeMapper.updateVideoLike(videoLike);
    }

    /**
     * 批量删除视频点赞
     *
     * @param ids 需要删除的视频点赞主键
     * @return 结果
     */
    @Override
    public int deleteVideoLikeByIds(Long[] ids)
    {
        return videoLikeMapper.deleteVideoLikeByIds(ids);
    }

    /**
     * 删除视频点赞信息
     *
     * @param id 视频点赞主键
     * @return 结果
     */
    @Override
    public int deleteVideoLikeById(Long id)
    {
        return videoLikeMapper.deleteVideoLikeById(id);
    }

    /**
     * 获取视频点赞列表
     * @oaram videoLike 视频点赞
     * @return 视频点赞列表
     */
    @Override
    public List<VideoLikeListVO> selectVideoLikeListVO(VideoLikeListDTO videoLike){
        return videoLikeMapper.selectVideoLikeListVO(videoLike);

    }
}
