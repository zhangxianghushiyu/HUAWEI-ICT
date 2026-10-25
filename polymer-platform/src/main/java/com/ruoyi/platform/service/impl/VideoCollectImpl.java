package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.Video;
import com.ruoyi.platform.domain.VideoCollect;
import com.ruoyi.platform.enums.DelStatusEnum;
import com.ruoyi.platform.mapper.VideoCollectDao;
import com.ruoyi.platform.mapper.VideoDao;
import com.ruoyi.platform.service.VideoCollectService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.MyVideoCollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class VideoCollectImpl implements VideoCollectService {
    @Autowired
    private VideoCollectDao videoCollectDao;
    @Autowired
    private VideoDao videoDao;
    @Autowired
    private HttpServletRequest request;



    @Override
    @Transactional
    public void collection(Integer id) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        Video video = videoDao.selectByPrimaryKey(id);
        if(Objects.isNull(video)){
            throw new ApiException("视频不存在");
        }
        if(video.getDelStatus().equals(DelStatusEnum.DELETE.getCode())){
            throw new ApiException("视频不存在");
        }
        VideoCollect videoCollect = videoCollectDao.selectVideoCollectionByUserIdAndVideoId(requestUserId, id);
        if(Objects.isNull(videoCollect)){
            //新增
            VideoCollect newVideoCollect = new VideoCollect();
            newVideoCollect.setCreateTime(new Date());
            newVideoCollect.setUserId(requestUserId);
            newVideoCollect.setVideoId(id);
            videoCollectDao.insertSelective(newVideoCollect);
            //加次数
            videoDao.addVideoCollectCount(id);
        }else {
            //删除
            videoCollectDao.deleteByPrimaryKey(videoCollect.getId());
            //减次数
            videoDao.subVideoCollectCount(id);
        }
    }

    @Override
    public List<MyVideoCollectVO> getMyVideoCollectVO(){
        return videoCollectDao.selectMyVideoCollectVO(JwtUtil.getRequestUserId(request));
    }
}
