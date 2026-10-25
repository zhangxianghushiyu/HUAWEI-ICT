package com.ruoyi.platform.service.impl;


import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.Video;
import com.ruoyi.platform.domain.VideoLike;
import com.ruoyi.platform.enums.DelStatusEnum;
import com.ruoyi.platform.mapper.VideoDao;
import com.ruoyi.platform.mapper.VideoLikeDao;
import com.ruoyi.platform.service.VideoLikeService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.MyVideoLikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class VideoLikeServiceImpl implements VideoLikeService {

    @Autowired
    private VideoLikeDao videoLikeDao;
    @Autowired
    private VideoDao videoDao;
    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional
    public void like(Integer id) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        Video video = videoDao.selectByPrimaryKey(id);
        if (Objects.isNull(video)){
            throw new ApiException("视频不存在");
        }
        if (video.getDelStatus().equals(DelStatusEnum.DELETE.getCode())){
            throw new ApiException("视频不存在");
        }
        VideoLike videoLike = videoLikeDao.selectVideoLikeByUserIdAndVideoId(requestUserId, id);
        if (Objects.isNull(videoLike)) {
            //新增
            VideoLike newVideoLike = new VideoLike();
            newVideoLike.setCreateTime(new Date());
            newVideoLike.setUserId(requestUserId);
            newVideoLike.setVideoId(id);
            videoLikeDao.insertSelective(newVideoLike);
            //视频点赞数+1
            videoDao.addVideoLikeCount(id);
        } else {
            //删除
            videoLikeDao.deleteByPrimaryKey(videoLike.getId());
            //视频点赞数-1
            videoDao.subVideoLikeCount(id);
        }

    }
    @Override
    public List<MyVideoLikeVO> getMyVideoLikeVO(){
        return videoLikeDao.selectMyVideoLikeVO(JwtUtil.getRequestUserId(request));
    }

}
