package com.ruoyi.platform.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.dto.MusicAnswerDTO;
import com.ruoyi.platform.enums.BarrierRewardEnum;
import com.ruoyi.platform.enums.UpStatusEnum;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.AnswerRecordService;
import com.ruoyi.platform.service.MusicStoreService;
import com.ruoyi.platform.service.UserGradeService;
import com.ruoyi.platform.service.UserService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.vo.MusicAnswerVO;
import com.ruoyi.platform.vo.MusicStoreVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class MusicStoreServiceImpl implements MusicStoreService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MusicStoreDao musicStoreDao;
    @Autowired
    private UserAnswerDao userAnswerDao;
    @Autowired
    private BarrierDao barrierDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserGradeService userGradeService;
    @Autowired
    private AnswerRecordService answerRecordService;

    @Override
    @Transactional
    public MusicStoreVO get() {
        Date date = new Date();
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        MusicStoreVO musicStoreVO = musicStoreDao.selectRandMusic();
        //处理答案顺序
        if (Objects.nonNull(musicStoreVO)) {
            int num = RandomUtil.randomInt(1, 3);
            if (num == 1) {
                musicStoreVO.setOption1(musicStoreVO.getAnswer());
                musicStoreVO.setOption2(musicStoreVO.getWrong());
            } else {
                musicStoreVO.setOption1(musicStoreVO.getWrong());
                musicStoreVO.setOption2(musicStoreVO.getAnswer());
            }
        }
        //查看用户最新关卡信息
        UserAnswer userAnswer = userAnswerDao.selectUserAnswerByUserIdAndRecently(requestUserId);
        if (Objects.isNull(userAnswer)) {
            //没答过
            //初始化一条数据，第一关
            Barrier barrier = barrierDao.selectMinBarrier();
            UserAnswer userAnswer1 = new UserAnswer();
            userAnswer1.setBarrier(barrier.getBarrier());
            userAnswer1.setNumber(0);
            userAnswer1.setUserId(requestUserId);
            userAnswer1.setCreateTime(date);
            userAnswerDao.insertSelective(userAnswer1);
            //重新查询
            userAnswer = userAnswerDao.selectUserAnswerByUserIdAndRecently(requestUserId);
            //赋值
            musicStoreVO.setBarrier(userAnswer.getBarrier());
            musicStoreVO.setOldNumber(userAnswer.getNumber());
            musicStoreVO.setNumber(barrier.getNumber());
            //查询是不是最后一关
            Barrier barrier2 = barrierDao.selectMinBarrierByOldValue(userAnswer.getBarrier());
            musicStoreVO.setMaxBarrier(Objects.isNull(barrier2) ? 1 : 0);
        } else {
            //答过题
            //查询这一关的信息
            Barrier barrier = barrierDao.selectBarrierInfo(userAnswer.getBarrier());
            //赋值
            musicStoreVO.setBarrier(userAnswer.getBarrier());
            musicStoreVO.setOldNumber(userAnswer.getNumber());
            musicStoreVO.setNumber(barrier.getNumber());
            //查询是不是最后一关
            Barrier barrier2 = barrierDao.selectMinBarrierByOldValue(userAnswer.getBarrier());
            musicStoreVO.setMaxBarrier(Objects.isNull(barrier2) ? 1 : 0);
        }
        //查询微信最大奖励
        //如果希望安全，建议将查看的歌曲ID进行保存至数据库中；
        // 答题是判断存入的歌曲ID是不是和保存的一致；如果整改，管理系统对歌曲进行删除或者禁用时需要对用户已经记录的数据进行修改
        return musicStoreVO;
    }


    @Override
    @Transactional
    public MusicAnswerVO answer(MusicAnswerDTO dto) {
        Date date = new Date();
        MusicAnswerVO vo = new MusicAnswerVO();
        vo.setBarrier(dto.getBarrier());
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        MusicStore musicStore = musicStoreDao.selectByPrimaryKey(dto.getId());
        //查询用户信息
        User user = userDao.selectByPrimaryKey(requestUserId);
        userService.checkUser(user);
        if (Objects.isNull(musicStore) || musicStore.getStatus().equals(UpStatusEnum.STATUS_0.getCode())) {
            throw new ApiException("歌曲信息不存在");
        }
        //查询当前关自己的答题记录，查询最新的是不是和传入的一致
        UserAnswer userAnswer = userAnswerDao.selectUserAnswerByUserIdAndRecently(requestUserId);
        if (Objects.isNull(userAnswer)) {
            throw new ApiException("答题记录不存在，数据错误");
        }
        //答题记录处理，查看是不是需要到下一关
        if (!userAnswer.getBarrier().equals(dto.getBarrier())) {
            throw new ApiException("提交的答题数据信息不一致");
        }
        //查询当前关信息
        Barrier barrier = barrierDao.selectBarrierInfo(dto.getBarrier());
        //查询还有没有下一关
        Barrier barrier1 = barrierDao.selectMinBarrierByOldValue(barrier.getBarrier());
        boolean answerResult = false;
        if (musicStore.getAnswer().equals(dto.getAnswer())) {
            answerResult = true;
            //回答正确
            vo.setResultCode(1);
            //需要正确的题数
            Integer number1 = barrier.getNumber();
            //已经答对的题数
            Integer number2 = userAnswer.getNumber();
            //修改数据
            userAnswer.setNumber(number2 + 1);
            userAnswerDao.updateByPrimaryKeySelective(userAnswer);
            //比较是不是需要通过
            if (number2 + 1 >= barrier.getNumber()) {
                //需要进行下一关
                //新增一条数据
                if (Objects.isNull(barrier1)) {
                    //没有配置下一关，那么就不进行升级关卡
                    vo.setIsTraverse(0);
                } else {
                    //配置了关卡，那么就进行升级关卡
                    UserAnswer userAnswer1 = new UserAnswer();
                    userAnswer1.setBarrier(barrier1.getBarrier());
                    userAnswer1.setNumber(0);
                    userAnswer1.setUserId(requestUserId);
                    userAnswer1.setCreateTime(date);
                    userAnswerDao.insertSelective(userAnswer1);
                    //修改用户关卡数
                    user.setBarrier(barrier1.getBarrier());
                    userDao.updateByPrimaryKeySelective(user);
                    //通关
                    vo.setIsTraverse(1);
                }
            } else {
                //不通关
                vo.setIsTraverse(0);
            }
            //是否满足通关奖励
            boolean flag1 = vo.getIsTraverse() == 1;
            //是否满足幸运奖励
            boolean flag2 = (number2 + 1) % 4 == 0;
            //处理返回的奖励问题
            if (flag1) {
                //符合通关奖励，只给通关奖励
                vo.setRewardType(BarrierRewardEnum.TYPE_1.getCode());
            } else if (flag2) {
                vo.setRewardType(BarrierRewardEnum.TYPE_2.getCode());
            }
        } else {
            //回答错误
            vo.setResultCode(0);
            //不通关
            vo.setIsTraverse(0);
        }
        //已通关，处理业务
        if (vo.getIsTraverse() == 1) {
            //处理升级问题
            userGradeService.handleUserGrade(user, date);
            //处理是否提现问题，自动生成1-2关提现数据
        }
        //添加答题记录
        answerRecordService.addAnswerRecord(requestUserId, dto.getBarrier(), musicStore.getName(), musicStore.getAnswer(), musicStore.getWrong(), musicStore.getUrl(),
                dto.getAnswer(), answerResult ? 1 : 0, date);
        return vo;
    }



    public static void main(String[] args) {
        BigDecimal bigDecimal1 = new BigDecimal(0.1234).setScale(2, RoundingMode.HALF_UP);
        System.out.println(bigDecimal1);
    }
}
