package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.Drama;
import com.ruoyi.platform.domain.DramaCollect;
import com.ruoyi.platform.enums.DelStatusEnum;
import com.ruoyi.platform.enums.UpStatusEnum;
import com.ruoyi.platform.mapper.DramaCollectDao;
import com.ruoyi.platform.mapper.DramaDao;
import com.ruoyi.platform.mapper.DramaDetailsDao;
import com.ruoyi.platform.mapper.DramaWatchDao;
import com.ruoyi.platform.service.DramaCollectService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.MyDramaCollectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class DramaCollectServiceImpl implements DramaCollectService {

    @Autowired
    private DramaCollectDao dramaCollectDao;
    @Autowired
    private DramaDetailsDao dramaDetailsDao;
    @Autowired
    private DramaDao dramaDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DramaWatchDao dramaWatchDao;

    @Override
    @Transactional
    public void collect(Integer dramaId) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        Drama drama = dramaDao.selectByPrimaryKey(dramaId);
        if (Objects.isNull(drama)|| DelStatusEnum.DELETE.getCode().equals(drama.getDelStatus())
        || UpStatusEnum.STATUS_0.getCode().equals(drama.getStatus())){
            throw new ApiException("不存在的短剧信息");
        }
        //查看用户之前是不是收藏过
        DramaCollect dramaCollect = dramaCollectDao.selectDramaCollectByUserIdAndDramaId(requestUserId, dramaId);
        if (Objects.nonNull(dramaCollect)){
            //取消收藏
            dramaCollectDao.deleteByPrimaryKey(dramaCollect.getId());
            //减次数
            dramaDetailsDao.subDramaDetailsCollectCount(dramaCollect.getId());
        }else{
            //添加收藏
            DramaCollect newDramaCollect = new DramaCollect();
            newDramaCollect.setCreateTime(new Date());
            newDramaCollect.setUserId(requestUserId);
            newDramaCollect.setDramaId(dramaId);
            dramaCollectDao.insertSelective(newDramaCollect);
            //加次数
            dramaDetailsDao.addDramaDetailsCollectCount(dramaId);
        }
    }


    @Override
    public List<MyDramaCollectVO> getMyDramaCollectVO() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        List<MyDramaCollectVO> myDramaCollectVOS = dramaCollectDao.selectMyDramaCollectVO(requestUserId);
        if (Objects.nonNull(myDramaCollectVOS)&&!myDramaCollectVOS.isEmpty()){
            for (MyDramaCollectVO myDramaCollectVO : myDramaCollectVOS) {
                DramaCollect dramaCollect = dramaCollectDao.selectDramaCollectByUserIdAndDramaId(requestUserId, myDramaCollectVO.getDramaId());
                if (Objects.nonNull(dramaCollect)){
                    myDramaCollectVO.setIsCollect(1);
                }
                //查看看到多少集
                Integer num = dramaWatchDao.selectWatchGatherByUserIdAndDramaId(requestUserId, myDramaCollectVO.getDramaId());
                if (Objects.nonNull(num)) {
                    myDramaCollectVO.setWatchGather(num);
                }
            }
        }
        return myDramaCollectVOS;
    }
}
