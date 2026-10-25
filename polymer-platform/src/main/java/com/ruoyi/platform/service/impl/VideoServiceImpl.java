package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.Video;
import com.ruoyi.platform.domain.VideoCollect;
import com.ruoyi.platform.domain.VideoLike;
import com.ruoyi.platform.enums.DelStatusEnum;
import com.ruoyi.platform.enums.UpStatusEnum;
import com.ruoyi.platform.mapper.VideoCollectDao;
import com.ruoyi.platform.mapper.VideoDao;
import com.ruoyi.platform.mapper.VideoLikeDao;
import com.ruoyi.platform.service.VideoService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.VideoListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;


@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoDao videoDao;
    @Autowired
    private VideoLikeDao videoLikeDao;
    @Autowired
    private VideoCollectDao videoCollectDao;
    @Autowired
    private HttpServletRequest request;

    /*
     * 获取视频详情
     */
    @Override
    public VideoListVO getVideoDetail(Integer id) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        VideoListVO videoDetail = new VideoListVO();
        Video video = videoDao.selectByPrimaryKey(id);
        if (Objects.isNull(video) || video.getDelStatus().equals(DelStatusEnum.DELETE.getCode()) ||
                video.getStatus().equals(UpStatusEnum.STATUS_0.getCode())) {
            throw new ApiException("视频信息不存在");
        }
        BeanUtils.copyProperties(video, videoDetail);
        //查询是不是点赞，或者收藏过
        VideoLike videoLike = videoLikeDao.selectVideoLikeByUserIdAndVideoId(requestUserId, video.getId());
        if (Objects.nonNull(videoLike)) {
            videoDetail.setIsLike(1);
        }
        VideoCollect videoCollect = videoCollectDao.selectVideoCollectionByUserIdAndVideoId(requestUserId, video.getId());
        if (Objects.nonNull(videoCollect)) {
            videoDetail.setIsCollect(1);
        }
        return videoDetail;
    }

    /*
     * 获取视频列表
     */
    @Override
    public List<VideoListVO> getVideoList() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        List<VideoListVO> videoList = videoDao.getVideoList();
        if (Objects.nonNull(videoList) && !videoList.isEmpty()) {
            for (VideoListVO videoListVO : videoList) {
                if (Objects.nonNull(requestUserId)) {
                    //查询是不是点赞，或者收藏过
                    VideoLike videoLike = videoLikeDao.selectVideoLikeByUserIdAndVideoId(requestUserId, videoListVO.getId());
                    if (Objects.nonNull(videoLike)) {
                        videoListVO.setIsLike(1);
                    }
                    VideoCollect videoCollect = videoCollectDao.selectVideoCollectionByUserIdAndVideoId(requestUserId, videoListVO.getId());
                    if (Objects.nonNull(videoCollect)) {
                        videoListVO.setIsCollect(1);
                    }
                }
            }
        }
        return videoList;
    }

    /*
     * 获取视频播放次数
     */
    public Integer updateVideoPlayCount(Integer id) {
        return videoDao.updateVideoPlayCount(id);
    }

}


