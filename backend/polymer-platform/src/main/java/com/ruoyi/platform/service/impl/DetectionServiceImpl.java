package com.ruoyi.platform.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.platform.constant.Constant;
import com.ruoyi.platform.domain.Detection;
import com.ruoyi.platform.domain.TaKuCallback;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.domain.UserDeactivate;
import com.ruoyi.platform.enums.DetectionTypeEnum;
import com.ruoyi.platform.enums.UserStatusEnum;
import com.ruoyi.platform.mapper.TaKuCallbackDao;
import com.ruoyi.platform.mapper.UserDao;
import com.ruoyi.platform.mapper.UserDeactivateDao;
import com.ruoyi.platform.service.DetectionService;
import com.ruoyi.platform.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DetectionServiceImpl implements DetectionService {

    @Autowired
    private UserDeactivateDao userDeactivateDao;
    @Autowired
    private TaKuCallbackDao taKuCallbackDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserDao userDao;


    /**
     * 自动封禁
     *
     * @param userId
     * @param date
     */
    private void handleDetection(Integer userId, Date date, String msg) {
        //自动封停
        User user = userDao.selectByPrimaryKey(userId);
        user.setStatus(UserStatusEnum.STATUS_0.getCode());
        //清除redis
        redisUtil.del(Constant.USER_LOGIN_TOKEN + user.getId());
        userDao.updateByPrimaryKeySelective(user);
        //添加记录
        UserDeactivate userDeactivate = new UserDeactivate();
        userDeactivate.setUserId(userId);
        userDeactivate.setCreateTime(date);
        userDeactivate.setRemarks(msg);
        userDeactivateDao.insertSelective(userDeactivate);
    }


    @Override
    @Transactional
    public Boolean handleDetection3(Detection detection, Integer userId, Date date) {
        //查看是不是开启了开关
        if (detection.getStatus() == 1) {
            log.info("开启了：" + DetectionTypeEnum.TYPE_3.getMessage() + " 风控检测");
            //查询用户上次广告信息
            TaKuCallback taKuCallback = taKuCallbackDao.selectTaKuCallbackRecently(userId);
            if (Objects.nonNull(taKuCallback)) {
                //查询两个时间的间隔时间
                long time = taKuCallback.getCreateTime().getTime();
                long time1 = date.getTime();
                long between = DateUtil.between(date, taKuCallback.getCreateTime(), DateUnit.SECOND);
                if (time1 > time) {
                    if (between < Integer.parseInt(detection.getValue1())) {
                        handleDetection(userId, date, DetectionTypeEnum.TYPE_3.getMessage());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean handleDetection4(Detection detection, Integer userId, Date date, String publisherRevenue) {
        if (detection.getStatus() == 1) {
            log.info("开启了：" + DetectionTypeEnum.TYPE_4.getMessage() + " 风控检测");
            BigDecimal bigDecimal1 = new BigDecimal(publisherRevenue);
            BigDecimal bigDecimal2 = new BigDecimal(detection.getValue1());
            if (bigDecimal1.compareTo(bigDecimal2) > 0) {
                //自动封停
                handleDetection(userId, date, DetectionTypeEnum.TYPE_4.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean handleDetection5(Detection detection, Integer userId, Date date, String publisherRevenue) {
        if (detection.getStatus() == 1) {
            log.info("开启了：" + DetectionTypeEnum.TYPE_5.getMessage() + " 风控检测");
            List<TaKuCallback> taKuCallbacks = taKuCallbackDao.selectContinuousTaKuCallback(userId, Integer.parseInt(detection.getValue1()));
            if (!taKuCallbacks.isEmpty() && taKuCallbacks.size() >= Integer.parseInt(detection.getValue1())) {
                boolean flag = false;
                int i=0;
                log.info("风控设置的金额是{}",detection.getValue2());
                for (TaKuCallback taKuCallback : taKuCallbacks) {
                    BigDecimal bigDecimal = new BigDecimal(taKuCallback.getPublisherRevenue());
                    if (bigDecimal.compareTo(new BigDecimal(detection.getValue2())) <= 0) {
                        i++;
                    }
                }
                if (i>=Integer.parseInt(detection.getValue1())){
                    flag = true;
                }
                if (flag) {
                    handleDetection(userId, date, DetectionTypeEnum.TYPE_5.getMessage());
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean handleDetection6(Detection detection, Integer userId, Date date, String publisherRevenue) {
        if (detection.getStatus() == 1) {
            log.info("开启了：" + DetectionTypeEnum.TYPE_6.getMessage() + " 风控检测");
            //查询用户当前登录的ip地址
            User user = userDao.selectByPrimaryKey(userId);
            if (Objects.nonNull(user) && StrUtil.isNotEmpty(user.getLastIp())) {
                //通过ip查询所有用户信息
                List<User> users = userDao.selectUserListByLastIp(user.getLastIp());
                if (!users.isEmpty() && users.size() >= Integer.parseInt(detection.getValue1())) {
                    //进行全体封禁
                    for (User user1 : users) {
                        handleDetection(user1.getId(), date, DetectionTypeEnum.TYPE_6.getMessage());
                    }
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    @Transactional
    public Boolean handleDetection7(Detection detection, Integer userId, Date date, String publisherRevenue) {
        if (detection.getStatus() == 1) {
            log.info("开启了：" + DetectionTypeEnum.TYPE_7.getMessage() + " 风控检测");
            //查询当天看的广告数量，如果超过这个数量，那么就进行自动封禁
            Integer count = taKuCallbackDao.selectTaKuCallbackCount(userId);
            if (count >= Integer.parseInt(detection.getValue1())) {
                handleDetection(userId, date, DetectionTypeEnum.TYPE_7.getMessage());
                return false;
            }
        }
        return true;
    }
}
