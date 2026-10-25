package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.enums.BillSceneTypeEnum;
import com.ruoyi.platform.enums.DelStatusEnum;
import com.ruoyi.platform.enums.UpStatusEnum;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.BillGoldService;
import com.ruoyi.platform.service.DramaService;
import com.ruoyi.platform.service.UserService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.DramaHomePageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DramaServiceImpl implements DramaService {

    @Autowired
    private DramaDao dramaDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private DramaWatchDao dramaWatchDao;
    @Autowired
    private DramaCollectDao dramaCollectDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private DramaUnlockDao dramaUnlockDao;
    @Autowired
    private BillGoldService billGoldService;
    @Autowired
    private DramaDetailsDao dramaDetailsDao;
    @Autowired
    private DramaPublicizeDao dramaPublicizeDao;

    @Override
    public List<DramaHomePageVO> homePage(Integer mainType,Integer categoryId) {
        List<DramaHomePageVO> dramaHomePageVOS = dramaDao.selectDramaHomePageVO(mainType,categoryId);
        if (Objects.nonNull(dramaHomePageVOS) && !dramaHomePageVOS.isEmpty()) {
            Integer requestUserId = JwtUtil.getRequestUserId(request);
            //已经登录，查看用户当前看到多少集
            for (DramaHomePageVO dramaHomePageVO : dramaHomePageVOS) {
                if (Objects.nonNull(requestUserId)) {
                    Integer num = dramaWatchDao.selectWatchGatherByUserIdAndDramaId(requestUserId, dramaHomePageVO.getDramaId());
                    if (Objects.nonNull(num)) {
                        dramaHomePageVO.setWatchGather(num);
                    }
                    DramaCollect dramaCollect = dramaCollectDao.selectDramaCollectByUserIdAndDramaId(requestUserId, dramaHomePageVO.getDramaId());
                    if (Objects.nonNull(dramaCollect)) {
                        dramaHomePageVO.setIsCollect(1);
                    }
                }
                dramaHomePageVO.setSumEpisodes(dramaDetailsDao.selectDramaDetailsListVOCount(dramaHomePageVO.getDramaId()));
            }
        }
        return dramaHomePageVOS;
    }

    @Override
    public List<DramaHomePageVO> query(String title) {
        List<DramaHomePageVO> dramaHomePageVOS = dramaDao.queryDrama(title);
        if (Objects.nonNull(dramaHomePageVOS) && !dramaHomePageVOS.isEmpty()) {
            //已经登录，查看用户当前看到多少集
            Integer requestUserId = JwtUtil.getRequestUserId(request);
            for (DramaHomePageVO dramaHomePageVO : dramaHomePageVOS) {
                if (Objects.nonNull(requestUserId)) {
                    Integer num = dramaWatchDao.selectWatchGatherByUserIdAndDramaId(requestUserId, dramaHomePageVO.getDramaId());
                    if (Objects.nonNull(num)) {
                        dramaHomePageVO.setWatchGather(num);
                    }
                }
                dramaHomePageVO.setSumEpisodes(dramaDetailsDao.selectDramaDetailsListVOCount(dramaHomePageVO.getDramaId()));
            }
        }
        return dramaHomePageVOS;
    }


    @Override
    @Transactional
    public void moneyUnlock(Integer dramaId) {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        User user = userDao.selectByPrimaryKey(requestUserId);
        userService.checkUser(user);
        //查询需要解锁时的金额
        DramaUnlock dramaUnlock = dramaUnlockDao.selectDramaUnlock();
        if (user.getGoldCoin().compareTo(dramaUnlock.getMoney()) < 0) {
            throw new ApiException("需要：" + dramaUnlock.getMoney() + " 金币才可进行解锁！");
        }
        Drama drama = dramaDao.selectByPrimaryKey(dramaId);
        if (Objects.isNull(drama) || drama.getStatus().equals(UpStatusEnum.STATUS_0.getCode()) || drama.getDelStatus().equals(DelStatusEnum.DELETE.getCode())) {
            throw new ApiException("短剧信息不存在");
        }
        //减金币，加账单，解锁
        Date date = new Date();
        userDao.subGoldCoin(requestUserId, dramaUnlock.getMoney());

        List<DramaDetails> list = dramaDetailsDao.
                selectNeedUnlockDramaDetailsId(requestUserId, dramaId, dramaUnlock.getUnlockNum());
        if (!list.isEmpty()) {
            //进行解锁，添加数据
            //先查看有没有数据，如果没有进行新增
            //处理回调信息
            for (DramaDetails dramaDetails : list) {
                DramaPublicize dramaPublicize1 = dramaPublicizeDao.
                        selectDramaPublicizeByUserIdAndDramaDetailsId(requestUserId, dramaDetails.getId());
                if (Objects.isNull(dramaPublicize1)) {
                    DramaPublicize dramaPublicize2 = new DramaPublicize();
                    dramaPublicize2.setCreateTime(date);
                    dramaPublicize2.setUserId(requestUserId);
                    dramaPublicize2.setDramaDetailsId(dramaDetails.getId());
                    dramaPublicize2.setUnlock(1);
                    dramaPublicizeDao.insertSelective(dramaPublicize2);
                }
            }
            List<Integer> gathers = list.stream().map(DramaDetails::getGather).collect(Collectors.toList());
            String s = "解锁短剧：" + drama.getTitle() + ";解锁集数：" +
                    gathers;
            billGoldService.addBillGold(requestUserId, BillSceneTypeEnum.SCENE_2.getCode(), s, dramaUnlock.getMoney(), date);
        }
    }
}
