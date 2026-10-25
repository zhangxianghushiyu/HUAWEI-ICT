package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.DramaCollect;
import com.ruoyi.platform.domain.DramaDetails;
import com.ruoyi.platform.domain.DramaLike;
import com.ruoyi.platform.enums.DelStatusEnum;
import com.ruoyi.platform.enums.UpStatusEnum;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.DramaLikeService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.MyDramaLikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class DramaLikeServiceImpl implements DramaLikeService {

    @Autowired
    private DramaLikeDao dramaLikeDao;
    @Autowired
    private DramaDao dramaDao;
    @Autowired
    private DramaDetailsDao dramaDetailsDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DramaCollectDao dramaCollectDao;
    @Autowired
    private DramaWatchDao dramaWatchDao;

    @Override
    @Transactional
    public void like(Integer dramaDetailsId) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        DramaDetails dramaDetails = dramaDetailsDao.selectByPrimaryKey(dramaDetailsId);
        if (Objects.isNull(dramaDetails) || DelStatusEnum.DELETE.getCode().equals(dramaDetails.getDelStatus())
                || UpStatusEnum.STATUS_0.getCode().equals(dramaDetails.getStatus())) {
            throw new ApiException("不存在的短剧信息");
        }
        //查看之前是是否已经点过
        DramaLike dramaLike = dramaLikeDao.selectDramaLikeByUserIdAndDramaDetailsId(requestUserId, dramaDetailsId);
        if (Objects.isNull(dramaLike)) {
            //新增
            DramaLike newDramaLike = new DramaLike();
            newDramaLike.setCreateTime(new Date());
            newDramaLike.setUserId(requestUserId);
            newDramaLike.setDramaDetailsId(dramaDetailsId);
            newDramaLike.setDramaId(dramaDetails.getDramaId());
            dramaLikeDao.insertSelective(newDramaLike);
            //加上主信息的点赞量
            dramaDao.addDramaLikeCount(dramaDetails.getDramaId());
            //该视频加点赞
            dramaDetailsDao.addDramaDetailsLikeCount(dramaDetailsId);
            //修改所有的更新时间
            dramaLikeDao.updateUpdateTimeByDramaIdAndUserId(dramaDetails.getDramaId(), requestUserId);
        } else {
            //删除
            dramaLikeDao.deleteByPrimaryKey(dramaLike.getId());
            //减去主信息的点赞量
            dramaDao.subDramaLikeCount(dramaDetails.getDramaId());
            //该视频减点赞
            dramaDetailsDao.subDramaDetailsLikeCount(dramaDetailsId);
        }
    }

    @Override
    public List<MyDramaLikeVO> getMyDramaLike() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        List<MyDramaLikeVO> myDramaLike = dramaLikeDao.getMyDramaLike(requestUserId);
        if (Objects.nonNull(myDramaLike)&&!myDramaLike.isEmpty()){
            for (MyDramaLikeVO myDramaLikeVO : myDramaLike) {
                DramaCollect dramaCollect = dramaCollectDao.selectDramaCollectByUserIdAndDramaId(requestUserId, myDramaLikeVO.getDramaId());
                if (Objects.nonNull(dramaCollect)){
                    myDramaLikeVO.setIsCollect(1);
                }
                //查看看到多少集
                Integer num = dramaWatchDao.selectWatchGatherByUserIdAndDramaId(requestUserId, myDramaLikeVO.getDramaId());
                if (Objects.nonNull(num)) {
                    myDramaLikeVO.setWatchGather(num);
                }
            }
        }
        return myDramaLike;
    }
}
