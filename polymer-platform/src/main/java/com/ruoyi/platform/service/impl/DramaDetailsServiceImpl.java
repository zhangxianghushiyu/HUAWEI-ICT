package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.enums.DelStatusEnum;
import com.ruoyi.platform.enums.UpStatusEnum;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.DramaDetailsService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.DramaDetailsListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DramaDetailsServiceImpl implements DramaDetailsService {

    @Autowired
    private DramaDetailsDao dramaDetailsDao;
    @Autowired
    private DramaCollectDao dramaCollectDao;
    @Autowired
    private DramaLikeDao dramaLikeDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DramaDao dramaDao;
    @Autowired
    private DramaPublicizeDao dramaPublicizeDao;
    @Autowired
    private DramaUnlockDao dramaUnlockDao;

    @Override
    public List<DramaDetailsListVO> getDramaDetailsListVO(Integer dramaId) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        //查看
        Drama drama = dramaDao.selectByPrimaryKey(dramaId);
        if (Objects.isNull(drama) || UpStatusEnum.STATUS_0.getCode().equals(drama.getStatus()) ||
                DelStatusEnum.DELETE.getCode().equals(drama.getDelStatus())) {
            throw new ApiException("短剧信息不存在");
        }
        //查看看一个广告解锁多少级
        DramaUnlock dramaUnlock = dramaUnlockDao.selectDramaUnlock();
        int freeNum = dramaUnlock != null && dramaUnlock.getFreeNum() != null ? dramaUnlock.getFreeNum() : 3;
        //创建返回对象
        List<DramaDetailsListVO> dramaDetailsListVOS;
        //查看
        dramaDetailsListVOS = dramaDetailsDao.selectDramaDetailsListVO(dramaId);
        if (!dramaDetailsListVOS.isEmpty()) {
            for (DramaDetailsListVO dramaDetailsListVO : dramaDetailsListVOS) {
                dramaDetailsListVO.setUnlockCount(dramaUnlock.getUnlockNum());
                //查看是不是需要收费，或者设置了看广告解锁；前freeNum集免费
                if (dramaDetailsListVO.getIsPrice() == 1
                        && (dramaDetailsListVO.getGather() == null || dramaDetailsListVO.getGather() > freeNum)) {
                    log.info("dramaDetailsListVOId:" + dramaDetailsListVO.getDramaDetailsId());
                    DramaPublicize dramaPublicize = dramaPublicizeDao.selectDramaPublicizeByUserIdAndDramaDetailsId(
                            requestUserId, dramaDetailsListVO.getDramaDetailsId());
                    if (Objects.isNull(dramaPublicize)) {
                        dramaDetailsListVO.setVideoUrl("");
                        dramaDetailsListVO.setContent("");
                    }
                }
                dramaDetailsListVO.setIsEnd(drama.getIsEnd());
                //查看是不是点赞过、收藏过
                handleLikeAndCollect(dramaDetailsListVO, requestUserId);
            }
        }
        return dramaDetailsListVOS;
    }


    /**
     * 处理是否点赞和收藏
     */
    private void handleLikeAndCollect(DramaDetailsListVO dramaDetailsListVO, Integer requestUserId) {
        //查看是不是收藏了该剧
        DramaCollect dramaCollect = dramaCollectDao.
                selectDramaCollectByUserIdAndDramaId(requestUserId, dramaDetailsListVO.getDramaId());
        if (Objects.nonNull(dramaCollect)) {
            dramaDetailsListVO.setIsCollect(1);
        }
        //查看是不是点赞过
        DramaLike dramaLike = dramaLikeDao.selectDramaLikeByUserIdAndDramaDetailsId(requestUserId, dramaDetailsListVO.getDramaDetailsId());
        if (Objects.nonNull(dramaLike)) {
            dramaDetailsListVO.setIsLike(1);
        }
    }
}
