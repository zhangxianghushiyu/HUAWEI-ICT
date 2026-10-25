package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.enums.DelStatusEnum;
import com.ruoyi.platform.enums.UpStatusEnum;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.DramaLookService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.MyDramaLookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DramaLookServiceImpl implements DramaLookService {

    @Autowired
    private DramaLookDao dramaLookDao;
    @Autowired
    private DramaWatchDao dramaWatchDao;
    @Autowired
    private DramaDao dramaDao;
    @Autowired
    private DramaDetailsDao dramaDetailsDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DramaCollectDao dramaCollectDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DramaPublicizeDao dramaPublicizeDao;
    @Autowired
    private DramaCategoryDao dramaCategoryDao;

    @Override
    @Transactional
    public void look(Integer dramaDetailsId) {
        Date date = new Date();
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        //查询之前有没有看过该部剧
        DramaDetails dramaDetails = dramaDetailsDao.selectByPrimaryKey(dramaDetailsId);
        if (Objects.isNull(dramaDetails) ||
                DelStatusEnum.DELETE.getCode().equals(dramaDetails.getDelStatus()) ||
                UpStatusEnum.STATUS_0.getCode().equals(dramaDetails.getStatus())) {
            return;
        }
        Drama drama = dramaDao.selectByPrimaryKey(dramaDetails.getDramaId());
        if (Objects.isNull(drama) ||
                DelStatusEnum.DELETE.getCode().equals(drama.getDelStatus()) ||
                UpStatusEnum.STATUS_0.getCode().equals(drama.getStatus())) {
            return;
        }
        User user = userDao.selectByPrimaryKey(requestUserId);
        if (Objects.isNull(user)) {
            return;
        }
        //查看之前是不是记录过(处理自己的浏览记录)
        DramaLook lookInfo = dramaLookDao.selectDramaLookByUserIdAndDramaId(requestUserId, drama.getId());
        if (Objects.isNull(lookInfo)) {
            DramaLook look = new DramaLook();
            look.setUserId(requestUserId);
            look.setDramaId(drama.getId());
            look.setCreateTime(date);
            look.setDramaDetailsId(dramaDetailsId);
            dramaLookDao.insertSelective(look);
        } else {
            //修改观看时间
            //修改观看集数
            lookInfo.setDramaDetailsId(dramaDetailsId);
            lookInfo.setCreateTime(date);
            dramaLookDao.updateByPrimaryKeySelective(lookInfo);
        }
        //查看之前是不是记录过(处理自己的观看集数记录)
        DramaWatch watchInfo = dramaWatchDao.selectDramaWatchByUserIdAndDramaId(requestUserId, drama.getId());
        if (Objects.isNull(watchInfo)) {
            DramaWatch watch = new DramaWatch();
            watch.setUserId(requestUserId);
            watch.setDramaId(drama.getId());
            watch.setCreateTime(date);
            watch.setDramaDetailsId(dramaDetailsId);
            dramaWatchDao.insertSelective(watch);
        } else {
            //修改观看集数
            watchInfo.setDramaDetailsId(dramaDetailsId);
            watchInfo.setCreateTime(date);
            dramaWatchDao.updateByPrimaryKeySelective(watchInfo);
        }
        //查看是不是有权限进行观看此集，如果没有权限观看，将不执行一下功能
        //查看是不是收费？
        if (dramaDetails.getIsPrice() == 1) {
            //是收费
            //查看是不是VIP，或者已经解锁了此集
            DramaPublicize dramaPublicize = dramaPublicizeDao.selectDramaPublicizeByUserIdAndDramaDetailsId(
                    requestUserId, dramaDetailsId);
            if (user.getIsVip() == 0 && Objects.isNull(dramaPublicize)) {
                //不是会员并且也没有解锁此集
                return;
            }
        }

    }


    @Override
    public List<MyDramaLookVO> getDramaLookRecordListVO() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        List<MyDramaLookVO> myDramaLookVOS = dramaLookDao.selectDramaLookRecordListVO(JwtUtil.getRequestUserId(request));
        //查看是否收藏过
        if (Objects.nonNull(myDramaLookVOS) && !myDramaLookVOS.isEmpty()) {
            for (MyDramaLookVO myDramaLookVO : myDramaLookVOS) {
                DramaCollect dramaCollect = dramaCollectDao.selectDramaCollectByUserIdAndDramaId(requestUserId, myDramaLookVO.getDramaId());
                if (Objects.nonNull(dramaCollect)) {
                    myDramaLookVO.setIsCollect(1);
                }
                //查看看到多少集
                Integer num = dramaWatchDao.selectWatchGatherByUserIdAndDramaId(requestUserId, myDramaLookVO.getDramaId());
                if (Objects.nonNull(num)) {
                    myDramaLookVO.setWatchGather(num);
                }
                myDramaLookVO.setSumEpisodes(dramaDetailsDao.selectDramaDetailsListVOCount(myDramaLookVO.getDramaId()));
                //查询分类名称
                DramaCategory dramaCategory = dramaCategoryDao.selectByPrimaryKey(myDramaLookVO.getClassifyId());
                myDramaLookVO.setClassifyIdStr(Objects.nonNull(dramaCategory) ? dramaCategory.getName() : null);
            }
        }
        return myDramaLookVOS;
    }


    @Override
    public void remove(Integer id) {
        dramaLookDao.removeInfoByDramaId(id);
    }
}
