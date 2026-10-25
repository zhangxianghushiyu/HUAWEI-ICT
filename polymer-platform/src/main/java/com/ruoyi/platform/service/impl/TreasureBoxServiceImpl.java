package com.ruoyi.platform.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.TreasureBox;
import com.ruoyi.platform.domain.TreasureBoxUser;
import com.ruoyi.platform.enums.BillSceneTypeEnum;
import com.ruoyi.platform.mapper.TreasureBoxDao;
import com.ruoyi.platform.mapper.TreasureBoxUserDao;
import com.ruoyi.platform.mapper.UserDao;
import com.ruoyi.platform.service.BillGoldService;
import com.ruoyi.platform.service.TreasureBoxService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.TreasureBoxVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class TreasureBoxServiceImpl implements TreasureBoxService {

    @Autowired
    private TreasureBoxDao treasureBoxDao;
    @Autowired
    private TreasureBoxUserDao treasureBoxUserDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BillGoldService billGoldService;

    @Override
    public TreasureBoxVO get() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        TreasureBoxVO vo = new TreasureBoxVO();
        //查看用户今天有没有点过？
        TreasureBoxUser treasureBoxUser = treasureBoxUserDao.selectLastTreasureBoxUserByUserId(requestUserId);
        TreasureBox treasureBox = treasureBoxDao.selectTreasureBox();
        if (Objects.isNull(treasureBoxUser)) {
            vo.setTodayIsReceive(0);
            vo.setTime(DateUtil.offsetMinute(new Date(), treasureBox.getMinute()));
        } else {
            vo.setTodayIsReceive(1);
            //推算时间，推指定分钟
            DateTime dateTime = DateUtil.offsetMinute(treasureBoxUser.getCreateTime(), treasureBox.getMinute());
            vo.setTime(dateTime);
        }
        vo.setSeconds(Integer.valueOf(treasureBox.getMinute() * 60));
        return vo;
    }


    @Override
    public BigDecimal receive() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        Date date = new Date();
        //查看配置的信息
        TreasureBox treasureBox = treasureBoxDao.selectTreasureBox();
        //查看当前时间是不是可以领取？
        //查看用户今天有没有点过？
        TreasureBoxUser treasureBoxUser = treasureBoxUserDao.selectLastTreasureBoxUserByUserId(requestUserId);
        if (Objects.nonNull(treasureBoxUser)) {
            //查看配置的信息
            //推算时间，推指定分钟
            DateTime dateTime = DateUtil.offsetMinute(treasureBoxUser.getCreateTime(), treasureBox.getMinute());
            //比较时间大小
            long time1 = date.getTime();
            long time2 = dateTime.getTime();
            ;
            // 获取一个Calendar实例，并设置为当前时间
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(treasureBoxUser.getCreateTime());
//
//            // 在Calendar上增加10秒
//            calendar.add(Calendar.SECOND, 10);
//            // 获取更新后的时间
//            long time2 = calendar.getTime().getTime();
            log.info("当前时间：{}", date);
            log.info("可领时间：{}", dateTime);
            if (time1 < time2) {
                throw  new ApiException("暂时无法领取，可领取时间为："+ dateTime);
            }
        }
        return treasureBox.getPrice();
    }


    @Override
    @Transactional
    public void obtain(Integer userId, Date date, BigDecimal money) {
        //查看配置的信息
        TreasureBox treasureBox = treasureBoxDao.selectTreasureBox();
        //领取
        TreasureBoxUser treasureBoxUser1 = new TreasureBoxUser();
        treasureBoxUser1.setUserId(userId);
        treasureBoxUser1.setPrice(BigDecimal.ZERO);
        treasureBoxUser1.setMinute(treasureBox.getMinute());
        treasureBoxUser1.setCreateTime(date);
        treasureBoxUserDao.insertSelective(treasureBoxUser1);
//        //加金币
//        log.info("看宝盒加金币：" + money);
//        userDao.addGoldCoin(userId, money);
//        //加账单
//        billGoldService.addBillGold(userId, BillSceneTypeEnum.SCENE_1.getCode(),
//                "宝盒领金币", money, date);
    }

    public static void main(String[] args) {
        Date date = new Date();
        DateTime newDate2 = DateUtil.offsetMinute(date, 3);
        log.info(newDate2.toString());
    }
}
