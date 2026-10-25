package com.ruoyi.platform.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.constant.Constant;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.enums.BillSceneTypeEnum;
import com.ruoyi.platform.enums.TaskFollowTypeEnum;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.BillGoldService;
import com.ruoyi.platform.service.DailyService;
import com.ruoyi.platform.service.TaskRewardService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.RedisUtil;
import com.ruoyi.platform.utils.SnowFlakeUtil;
import com.ruoyi.platform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DailyServiceImpl implements DailyService {

    @Autowired
    private TaskRewardDao taskRewardDao;
    @Autowired
    private TaskRewardService taskRewardService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TaskCompleteRecordDao taskCompleteRecordDao;
    @Autowired
    private TaskDailySignRecordDao taskDailySignRecordDao;
    @Autowired
    private TaskDailySignDao taskDailySignDao;
    @Autowired
    private BillGoldService billGoldService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TaskDailyFoodPointLookDramaDao taskDailyFoodPointLookDramaDao;
    @Autowired
    private TaskDailyLookDramaToCoinDao taskDailyLookDramaToCoinDao;
    @Autowired
    private TaskDailyLookDramaToCoinRecordDao taskDailyLookDramaToCoinRecordDao;
    @Autowired
    private TaskDailyLookVideoToCoinDao taskDailyLookVideoToCoinDao;
    @Autowired
    private TaskDailyLookVideoToCoinRecordDao taskDailyLookVideoToCoinRecordDao;
    @Autowired
    private TaskDailyAppointDramaCoinDao taskDailyAppointDramaCoinDao;
    @Autowired
    private TaskDailyAppointDramaCoinInfoDao taskDailyAppointDramaCoinInfoDao;
    @Autowired
    private TaskDailyAppointDramaCoinRecordDao taskDailyAppointDramaCoinRecordDao;
    @Autowired
    private DramaDao dramaDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private DramaWatchDao dramaWatchDao;

    @Override
    public DailyTaskListVO daily() {
        String today = DateUtil.today();
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        DailyTaskListVO dailyTaskListVO = new DailyTaskListVO();
        //查询用户当天有没有完成每日签到任务
        //查看是否开启
        Boolean b5 = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_5.getCode());
        if (b5) {
            Object object5 = redisUtil.get(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_5.getCode());
            if (Objects.isNull(object5)) {
                TaskCompleteRecord record1 = taskCompleteRecordDao.selectTaskCompleteRecordByTypeDaily(requestUserId,
                        TaskFollowTypeEnum.TYPE_5.getCode());
                if (Objects.isNull(record1)) {
                    TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_5.getCode());
                    DailySignVO dailySignVO = new DailySignVO();
                    BeanUtils.copyProperties(taskReward, dailySignVO);
                    dailyTaskListVO.setDailySignVO(dailySignVO);

                    List<DailySignInForSevenDaysDetailVO> dailySignInForSevenDaysDetailVOS;
                    //查看用户目前最近的一次签到
                    TaskDailySignRecord taskDailySignRecord = taskDailySignRecordDao.selectLimitTaskDailySignRecordInfo(requestUserId);
                    if (Objects.isNull(taskDailySignRecord)) {
                        //之前没有签到过
                        //需要重新生成初始化数据进行签到
                        String batchNo = SnowFlakeUtil.getId().toString();
                        dailySignInForSevenDaysDetailVOS = taskDailySignDao.
                                selectDailySignInForSevenDaysDetailVO(requestUserId, batchNo);
                    } else {
                        //拿到批号，查看此批号目前已经签到多少天
                        String batchNo = taskDailySignRecord.getBatchNo();
                        Integer count = taskDailySignRecordDao.selectTaskDailySignRecordInfoByBatchNo(requestUserId, batchNo);
                        if (count >= 7) {
                            //需要重新生成初始化数据进行签到
                            String batchNo2 = SnowFlakeUtil.getId().toString();
                            dailySignInForSevenDaysDetailVOS = taskDailySignDao.
                                    selectDailySignInForSevenDaysDetailVO(requestUserId, batchNo2);
                        } else {
                            dailySignInForSevenDaysDetailVOS = taskDailySignDao.
                                    selectDailySignInForSevenDaysDetailVO(requestUserId, taskDailySignRecord.getBatchNo());
                        }
                    }
                    for (DailySignInForSevenDaysDetailVO dailySignInForSevenDaysDetailVO : dailySignInForSevenDaysDetailVOS) {
                        if (dailySignInForSevenDaysDetailVO.getSignRecordId() != null) {
                            dailySignInForSevenDaysDetailVO.setIsSign(1);
                        }
                        dailySignInForSevenDaysDetailVO.setDayStr(dailySignInForSevenDaysDetailVO.getSort() + "天");
                    }
                    dailySignVO.setDailySignInForSevenDaysDetailVOS(dailySignInForSevenDaysDetailVOS);
                } else {
                    redisUtil.set(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_5.getCode(),
                            Constant.REDIS_DEFAULT_VALUE, 25L, TimeUnit.HOURS);
                }
            }
        }

        //查询用户当天有没有完成饭点看剧领补贴任务
        //查看是否开启
        Boolean b6 = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_6.getCode());
        if (b6) {
            Object object6 = redisUtil.get(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_6.getCode());
            if (Objects.isNull(object6)) {
                TaskCompleteRecord record2 = taskCompleteRecordDao.selectTaskCompleteRecordByTypeDaily(requestUserId,
                        TaskFollowTypeEnum.TYPE_6.getCode());
                if (Objects.isNull(record2)) {
                    TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_6.getCode());
                    FoodPointLookDramaVO foodPointLookDramaVO = new FoodPointLookDramaVO();
                    BeanUtils.copyProperties(taskReward, foodPointLookDramaVO);
                    dailyTaskListVO.setFoodPointLookDrama(foodPointLookDramaVO);
                } else {
                    redisUtil.set(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_6.getCode(),
                            Constant.REDIS_DEFAULT_VALUE, 25L, TimeUnit.HOURS);
                }
            }
        }
        //查询当天有没有完成看剧领金币任务
        //查看是否开启
        Boolean b7 = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_7.getCode());
        if (b7) {
            Object object7 = redisUtil.get(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_7.getCode());
            if (Objects.isNull(object7)) {
                TaskDailyLookDramaToCoin taskDailyLookDramaToCoin = taskDailyLookDramaToCoinDao.
                        selectMaxTaskDailyLookDramaToCoin();
                TaskDailyLookDramaToCoinRecord taskDailyLookDramaToCoinRecord = taskDailyLookDramaToCoinRecordDao.selectTaskDailyLookDramaToCoinRecordTheSameDay(requestUserId);
                if (Objects.isNull(taskDailyLookDramaToCoinRecord) || taskDailyLookDramaToCoin.getSecondInfo() > taskDailyLookDramaToCoinRecord.getSecondInfo()) {
                    TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_7.getCode());
                    LookDramaToCoinVO lookDramaToCoinVO = new LookDramaToCoinVO();
                    BeanUtils.copyProperties(taskReward, lookDramaToCoinVO);
                    dailyTaskListVO.setLookDramaToCoinVO(lookDramaToCoinVO);
                    List<TaskDailyLookDramaToCoin> taskDailyLookDramaToCoins = taskDailyLookDramaToCoinDao.
                            selectAllTaskDailyLookDramaToCoin();
                    List<LookDramaToCoinDetailVO> lookDramaToCoinDetailVOS = new ArrayList<>();
                    Integer lookSecond = 0;
                    if (Objects.nonNull(taskDailyLookDramaToCoinRecord)) {
                        lookSecond = taskDailyLookDramaToCoinRecord.getSecondInfo();
                    }
                    for (TaskDailyLookDramaToCoin dailyLookDramaToCoin : taskDailyLookDramaToCoins) {
                        LookDramaToCoinDetailVO lookDramaToCoinDetailVO = new LookDramaToCoinDetailVO();
                        lookDramaToCoinDetailVO.setId(dailyLookDramaToCoin.getId());
                        lookDramaToCoinDetailVO.setSecondInfo(dailyLookDramaToCoin.getSecondInfo());
                        lookDramaToCoinDetailVO.setMinuteStr(dailyLookDramaToCoin.getSecondInfo() / 60 + "分钟");
                        lookDramaToCoinDetailVO.setPrice(dailyLookDramaToCoin.getPrice());
                        if (lookSecond >= dailyLookDramaToCoin.getSecondInfo()) {
                            lookDramaToCoinDetailVO.setIsReceive(1);
                        }
                        lookDramaToCoinDetailVOS.add(lookDramaToCoinDetailVO);
                    }
                    lookDramaToCoinVO.setLookDramaToCoinDetailVOS(lookDramaToCoinDetailVOS);
                } else {
                    redisUtil.set(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_7.getCode(),
                            Constant.REDIS_DEFAULT_VALUE, 25L, TimeUnit.HOURS);
                }
            }
        }

        //查询当天有没有完成看小视频领金币任务
        //查看是否开启
        Boolean b8 = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_8.getCode());
        if (b8) {
            Object object8 = redisUtil.get(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_8.getCode());
            if (Objects.isNull(object8)) {
                TaskDailyLookVideoToCoin taskDailyLookVideoToCoin = taskDailyLookVideoToCoinDao.selectTaskDailyLookVideoToCoinInfo();
                TaskDailyLookVideoToCoinRecord taskDailyLookVideoToCoinRecord = taskDailyLookVideoToCoinRecordDao.selectTaskDailyLookVideoToCoinRecordTheSameDay(requestUserId);
                if (Objects.isNull(taskDailyLookVideoToCoinRecord) ||
                        taskDailyLookVideoToCoinRecord.getCount() < taskDailyLookVideoToCoin.getCount()) {
                    TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_8.getCode());
                    LookVideoToCoinVO lookVideoToCoinVO = new LookVideoToCoinVO();
                    BeanUtils.copyProperties(taskReward, lookVideoToCoinVO);
                    //赋值配置的信息以及目前观看的次数
                    lookVideoToCoinVO.setPrice(taskDailyLookVideoToCoin.getPrice());
                    lookVideoToCoinVO.setNeedCount(taskDailyLookVideoToCoin.getCount());
                    lookVideoToCoinVO.setCount(Objects.isNull(taskDailyLookVideoToCoinRecord) ? 0 : taskDailyLookVideoToCoinRecord.getCount());
                    dailyTaskListVO.setLookVideoToCoinVO(lookVideoToCoinVO);
                } else {
                    redisUtil.set(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_8.getCode(),
                            Constant.REDIS_DEFAULT_VALUE, 25L, TimeUnit.HOURS);
                }
            }
        }

        //查询当天有没有完成分享领金币任务
        //查看是否开启
        Boolean b9 = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_9.getCode());
        if (b9) {
            Object object9 = redisUtil.get(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_9.getCode());
            if (Objects.isNull(object9)) {
                TaskCompleteRecord taskCompleteRecord = taskCompleteRecordDao.
                        selectTaskCompleteRecordByTypeDaily(requestUserId, TaskFollowTypeEnum.TYPE_9.getCode());
                if (Objects.isNull(taskCompleteRecord)) {
                    TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_9.getCode());
                    ShareToCoinVO shareToCoinVO = new ShareToCoinVO();
                    BeanUtils.copyProperties(taskReward, shareToCoinVO);
                    dailyTaskListVO.setShareToCoinVO(shareToCoinVO);
                } else {
                    redisUtil.set(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_9.getCode(),
                            Constant.REDIS_DEFAULT_VALUE, 25L, TimeUnit.HOURS);
                }
            }
        }
        //查询当天有没有完成看指定剧领金币任务
        //查看是否开启
        Boolean b10 = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_10.getCode());
        if (b10) {
            Object object10 = redisUtil.get(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_10.getCode());
            if (Objects.isNull(object10)) {
                TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo = taskDailyAppointDramaCoinInfoDao.
                        selectTaskDailyAppointDramaCoinInfoInfo();
                TaskDailyAppointDramaCoinRecord taskDailyAppointDramaCoinRecord = taskDailyAppointDramaCoinRecordDao.
                        selectTaskDailyAppointDramaCoinRecordTheSameDay(requestUserId);
                if (Objects.isNull(taskDailyAppointDramaCoinRecord) || taskDailyAppointDramaCoinInfo.getSecondInfo() > taskDailyAppointDramaCoinRecord.getSecondInfo()) {
                    TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_10.getCode());
                    AppointDramaCoinVO appointDramaCoinVO = new AppointDramaCoinVO();
                    BeanUtils.copyProperties(taskReward, appointDramaCoinVO);
                    dailyTaskListVO.setAppointDramaCoinVO(appointDramaCoinVO);
                    List<AppointDramaCoinListVO> appointDramaCoinListVOS = dramaDao.selectAppointDramaCoinListVOInfo();
                    if (!appointDramaCoinListVOS.isEmpty()){
                        //处理当前观看的集数
                        for (AppointDramaCoinListVO appointDramaCoinListVO : appointDramaCoinListVOS) {
                            Integer num = dramaWatchDao.selectWatchGatherByUserIdAndDramaId(requestUserId,
                                    appointDramaCoinListVO.getDramaId());
                            if (Objects.nonNull(num)) {
                                appointDramaCoinListVO.setWatchGather(num);
                            }
                        }
                    }
                    appointDramaCoinVO.setAppointDramaCoinListVOS(appointDramaCoinListVOS);
                    //已看时间赋值
                    appointDramaCoinVO.setAlreadyViewed(
                            Objects.isNull(taskDailyAppointDramaCoinRecord) ? 0 : taskDailyAppointDramaCoinRecord.getSecondInfo()
                    );
                } else {
                    redisUtil.set(Constant.TASK + today + ":" + requestUserId + ":" + TaskFollowTypeEnum.TYPE_10.getCode(),
                            Constant.REDIS_DEFAULT_VALUE, 25L, TimeUnit.HOURS);
                }
            }
        }
        return dailyTaskListVO;
    }


    @Override
    @Transactional
    public void dailySignToGoldCoins() {
        Boolean b = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_5.getCode());
        if (!b) {
            //任务没有开启
            throw new ApiException("任务没有开启");
        }
        Integer requestUserId = JwtUtil.getRequestUserId(request);
//查看主任务是不是已经完成
        TaskCompleteRecord record2 = taskCompleteRecordDao.selectTaskCompleteRecordByTypeDaily(requestUserId,
                TaskFollowTypeEnum.TYPE_5.getCode());
        if (Objects.nonNull(record2)) {
            throw new ApiException("该任务已经完成");
        }
        List<DailySignInForSevenDaysDetailVO> dailySignInForSevenDaysDetailVOS;
        //查看用户目前最近的一次签到
        TaskDailySignRecord taskDailySignRecord = taskDailySignRecordDao.selectLimitTaskDailySignRecordInfo(requestUserId);
        String batchNo;
        if (Objects.isNull(taskDailySignRecord)) {
            //之前没有签到过
            //需要重新生成初始化数据进行签到
            batchNo = SnowFlakeUtil.getId().toString();
            dailySignInForSevenDaysDetailVOS = taskDailySignDao.
                    selectDailySignInForSevenDaysDetailVO(requestUserId, batchNo);
        } else {
            //拿到批号，查看此批号目前已经签到多少天
            batchNo = taskDailySignRecord.getBatchNo();
            Integer count = taskDailySignRecordDao.selectTaskDailySignRecordInfoByBatchNo(requestUserId, batchNo);
            if (count >= 7) {
                //需要重新生成初始化数据进行签到
                batchNo = SnowFlakeUtil.getId().toString();
                dailySignInForSevenDaysDetailVOS = taskDailySignDao.
                        selectDailySignInForSevenDaysDetailVO(requestUserId, batchNo);
            } else {
                batchNo = taskDailySignRecord.getBatchNo();
                dailySignInForSevenDaysDetailVOS = taskDailySignDao.
                        selectDailySignInForSevenDaysDetailVO(requestUserId, taskDailySignRecord.getBatchNo());
            }
        }
        //查询签到的信息集合
        Integer needSignId = null;
        for (DailySignInForSevenDaysDetailVO dailySignInForSevenDaysDetailVO : dailySignInForSevenDaysDetailVOS) {
            if (dailySignInForSevenDaysDetailVO.getSignRecordId() == null) {
                needSignId = dailySignInForSevenDaysDetailVO.getId();
                break;
            }
        }
        Date date = new Date();
        //查询签到信息
        TaskDailySign taskDailySign = taskDailySignDao.selectByPrimaryKey(needSignId);
        //加金币
        userDao.addGoldCoin(requestUserId, taskDailySign.getPrice());
        //添加一条记录
        TaskDailySignRecord record = new TaskDailySignRecord();
        record.setUserId(requestUserId);
        record.setPrice(taskDailySign.getPrice());
        record.setCreateTime(date);
        record.setDailySignId(taskDailySign.getId());
        record.setBatchNo(batchNo);
        taskDailySignRecordDao.insertSelective(record);
        //添加到金币详细中
        billGoldService.addBillGold(requestUserId, BillSceneTypeEnum.SCENE_1.getCode(),
                TaskFollowTypeEnum.TYPE_5.getMessage(), taskDailySign.getPrice(), date);
        TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_5.getCode());
        //添加完成记录
        TaskCompleteRecord record3 = new TaskCompleteRecord();
        record3.setUserId(requestUserId);
        record3.setTaskRewardId(task.getId());
        record3.setTaskTitle(task.getTitle());
        record3.setTaskDescribe(task.getDescribe());
        record3.setTaskType(task.getType());
        record3.setTaskFollowType(task.getFollowType());
        record3.setTaskRewardType(task.getRewardType());
        record3.setTaskPrice(taskDailySign.getPrice());
        record3.setDescribe(TaskFollowTypeEnum.TYPE_5.getMessage());
        record3.setCreateTime(date);
        taskCompleteRecordDao.insertSelective(record3);
    }


    @Override
    @Transactional
    public synchronized void foodPointLookDramaToGoldCoins(Integer userId) {
        try {
            Boolean b = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_6.getCode());
            if (!b) {
                //任务没有开启
                return;
            }
            String now = DateUtil.now();
            DateTime date = DateUtil.parse(now);
            long time1 = DateUtil.parse(now).getTime();
            String todayTime = now.substring(0, now.indexOf(" "));
            List<TaskDailyFoodPointLookDrama> taskDailyFoodPointLookDramas = taskDailyFoodPointLookDramaDao.
                    selectAllTaskDailyFoodPointLookDrama();
            if (Objects.nonNull(taskDailyFoodPointLookDramas) && !taskDailyFoodPointLookDramas.isEmpty()) {
                for (TaskDailyFoodPointLookDrama taskDailyFoodPointLookDrama : taskDailyFoodPointLookDramas) {
                    String startTime = todayTime + " " + taskDailyFoodPointLookDrama.getStartTime();
                    String endTime = todayTime + " " + taskDailyFoodPointLookDrama.getEndTime();
                    long time2 = DateUtil.parse(startTime).getTime();
                    long time3 = DateUtil.parse(endTime).getTime();
                    log.info("time1:{},time2:{},time3:{}", time1, time2, time3);
                    if (time1 >= time2 && time1 <= time3) {
                        //查看当前时间点
                        //查询用户当天有没有完成饭点看剧领补贴任务
                        TaskCompleteRecord record2 = taskCompleteRecordDao.selectTaskCompleteRecordByTypeDaily(userId,
                                TaskFollowTypeEnum.TYPE_6.getCode());
                        if (Objects.isNull(record2)) {
                            TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_6.getCode());
//添加完成记录
                            TaskCompleteRecord record3 = new TaskCompleteRecord();
                            record3.setUserId(userId);
                            record3.setTaskRewardId(task.getId());
                            record3.setTaskTitle(task.getTitle());
                            record3.setTaskDescribe(task.getDescribe());
                            record3.setTaskType(task.getType());
                            record3.setTaskFollowType(task.getFollowType());
                            record3.setTaskRewardType(task.getRewardType());
                            record3.setTaskPrice(task.getPrice());
                            record3.setDescribe(TaskFollowTypeEnum.TYPE_6.getMessage());
                            record3.setCreateTime(date);
                            taskCompleteRecordDao.insertSelective(record3);
                            //加金币
                            userDao.addGoldCoin(userId, task.getPrice());
                            //添加到金币详细中
                            billGoldService.addBillGold(userId, BillSceneTypeEnum.SCENE_1.getCode(),
                                    TaskFollowTypeEnum.TYPE_6.getMessage(), task.getPrice(), date);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("处理饭点看剧时出现异常{}", e.getMessage());
        }
    }


    @Override
    @Transactional
    public void lookDramaToCoin(Integer userId, Integer second) {
        Boolean b = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_7.getCode());
        if (!b) {
            //任务没有开启
            return;
        }
        //查询当天有没有完成看剧领金币任务
        Date date = new Date();
        TaskDailyLookDramaToCoin taskDailyLookDramaToCoin = taskDailyLookDramaToCoinDao.
                selectMaxTaskDailyLookDramaToCoin();
        TaskDailyLookDramaToCoinRecord taskDailyLookDramaToCoinRecord = taskDailyLookDramaToCoinRecordDao.selectTaskDailyLookDramaToCoinRecordTheSameDay(userId);
        if (Objects.isNull(taskDailyLookDramaToCoinRecord)) {
            //加秒数
            TaskDailyLookDramaToCoinRecord taskDailyLookDramaToCoinRecord1 = new TaskDailyLookDramaToCoinRecord();
            taskDailyLookDramaToCoinRecord1.setUserId(userId);
            taskDailyLookDramaToCoinRecord1.setCreateTime(date);
            taskDailyLookDramaToCoinRecord1.setSecondInfo(second);
            taskDailyLookDramaToCoinRecordDao.insertSelective(taskDailyLookDramaToCoinRecord1);
            List<TaskDailyLookDramaToCoin> taskDailyLookDramaToCoins = taskDailyLookDramaToCoinDao.
                    selectAllTaskDailyLookDramaToCoin();
            TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_7.getCode());
            for (TaskDailyLookDramaToCoin dailyLookDramaToCoin : taskDailyLookDramaToCoins) {
                if (second >= dailyLookDramaToCoin.getSecondInfo()) {
                    //加金币，加记录,
                    //加金币
                    userDao.addGoldCoin(userId, dailyLookDramaToCoin.getPrice());
                    //添加到金币详细中
                    billGoldService.addBillGold(userId, BillSceneTypeEnum.SCENE_1.getCode(),
                            TaskFollowTypeEnum.TYPE_7.getMessage(), dailyLookDramaToCoin.getPrice(), date);
                    //添加完成记录
                    TaskCompleteRecord record3 = new TaskCompleteRecord();
                    record3.setUserId(userId);
                    record3.setTaskRewardId(task.getId());
                    record3.setTaskTitle(task.getTitle());
                    record3.setTaskDescribe(task.getDescribe());
                    record3.setTaskType(task.getType());
                    record3.setTaskFollowType(task.getFollowType());
                    record3.setTaskRewardType(task.getRewardType());
                    record3.setTaskPrice(dailyLookDramaToCoin.getPrice());
                    record3.setDescribe(TaskFollowTypeEnum.TYPE_7.getMessage());
                    record3.setCreateTime(date);
                    taskCompleteRecordDao.insertSelective(record3);
                }
            }
        } else {
            if (taskDailyLookDramaToCoin.getSecondInfo() > taskDailyLookDramaToCoinRecord.getSecondInfo()) {
                TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_7.getCode());
                List<TaskDailyLookDramaToCoin> taskDailyLookDramaToCoins = taskDailyLookDramaToCoinDao.
                        selectAllTaskDailyLookDramaToCoin();
                List<LookDramaToCoinDetailVO> lookDramaToCoinDetailVOS = new ArrayList<>();
                Integer lookMinute = taskDailyLookDramaToCoinRecord.getSecondInfo();
                for (TaskDailyLookDramaToCoin dailyLookDramaToCoin : taskDailyLookDramaToCoins) {
                    if (lookMinute < dailyLookDramaToCoin.getSecondInfo()) {
                        LookDramaToCoinDetailVO lookDramaToCoinDetailVO = new LookDramaToCoinDetailVO();
                        lookDramaToCoinDetailVO.setId(dailyLookDramaToCoin.getId());
                        lookDramaToCoinDetailVO.setPrice(dailyLookDramaToCoin.getPrice());
                        lookDramaToCoinDetailVO.setSecondInfo(dailyLookDramaToCoin.getSecondInfo());
                        lookDramaToCoinDetailVOS.add(lookDramaToCoinDetailVO);
                    }
                }
                //加秒数
                int sumSecond = taskDailyLookDramaToCoinRecord.getSecondInfo() + second;
                taskDailyLookDramaToCoinRecordDao.addLookSecondInfo(taskDailyLookDramaToCoinRecord.getId(), second);
                if (!lookDramaToCoinDetailVOS.isEmpty()) {
                    for (LookDramaToCoinDetailVO lookDramaToCoinDetailVO : lookDramaToCoinDetailVOS) {
                        if (sumSecond >= lookDramaToCoinDetailVO.getSecondInfo()) {
                            //加金币，加记录,
                            //加金币
                            userDao.addGoldCoin(userId, lookDramaToCoinDetailVO.getPrice());
                            //添加到金币详细中
                            billGoldService.addBillGold(userId, BillSceneTypeEnum.SCENE_1.getCode(),
                                    TaskFollowTypeEnum.TYPE_7.getMessage(), lookDramaToCoinDetailVO.getPrice(), date);
                            //添加完成记录
                            TaskCompleteRecord record3 = new TaskCompleteRecord();
                            record3.setUserId(userId);
                            record3.setTaskRewardId(task.getId());
                            record3.setTaskTitle(task.getTitle());
                            record3.setTaskDescribe(task.getDescribe());
                            record3.setTaskType(task.getType());
                            record3.setTaskFollowType(task.getFollowType());
                            record3.setTaskRewardType(task.getRewardType());
                            record3.setTaskPrice(lookDramaToCoinDetailVO.getPrice());
                            record3.setDescribe(TaskFollowTypeEnum.TYPE_7.getMessage());
                            record3.setCreateTime(date);
                            taskCompleteRecordDao.insertSelective(record3);
                        }
                    }
                }
            }
        }
    }


    @Transactional
    @Override
    public void lookVideoToCoin(Integer userId, Date date) {
        Boolean b = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_8.getCode());
        if (!b) {
            //任务没有开启
            return;
        }
        //查询当天有没有完成看小视频领金币任务
        TaskDailyLookVideoToCoinRecord taskDailyLookVideoToCoinRecord = taskDailyLookVideoToCoinRecordDao.selectTaskDailyLookVideoToCoinRecordTheSameDay(userId);
        boolean flag = false;
        if (Objects.isNull(taskDailyLookVideoToCoinRecord)) {
            //新增
            TaskDailyLookVideoToCoinRecord taskDailyLookVideoToCoinRecord1 = new TaskDailyLookVideoToCoinRecord();
            taskDailyLookVideoToCoinRecord1.setCount(1);
            taskDailyLookVideoToCoinRecord1.setUserId(userId);
            taskDailyLookVideoToCoinRecord1.setCreateTime(date);
            taskDailyLookVideoToCoinRecordDao.insertSelective(taskDailyLookVideoToCoinRecord1);
            flag = true;
        } else {
            TaskDailyLookVideoToCoin taskDailyLookVideoToCoin = taskDailyLookVideoToCoinDao.selectTaskDailyLookVideoToCoinInfo();
            if (taskDailyLookVideoToCoinRecord.getCount() < taskDailyLookVideoToCoin.getCount()) {
                //加次数
                taskDailyLookVideoToCoinRecordDao.addTaskDailyLookVideoToCoinRecordCount(taskDailyLookVideoToCoinRecord.getId());
                flag = true;
            }
        }
        if (flag) {
            TaskDailyLookVideoToCoin taskDailyLookVideoToCoin = taskDailyLookVideoToCoinDao.selectTaskDailyLookVideoToCoinInfo();
            taskDailyLookVideoToCoinRecord = taskDailyLookVideoToCoinRecordDao.selectTaskDailyLookVideoToCoinRecordTheSameDay(userId);
            if (taskDailyLookVideoToCoinRecord.getCount() >= taskDailyLookVideoToCoin.getCount()) {
                TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_8.getCode());
                //加金币，加记录,
                //加金币
                userDao.addGoldCoin(userId, taskDailyLookVideoToCoin.getPrice());
                //添加到金币详细中
                billGoldService.addBillGold(userId, BillSceneTypeEnum.SCENE_1.getCode(),
                        TaskFollowTypeEnum.TYPE_8.getMessage(), taskDailyLookVideoToCoin.getPrice(), date);
                //添加完成记录
                TaskCompleteRecord record3 = new TaskCompleteRecord();
                record3.setUserId(userId);
                record3.setTaskRewardId(task.getId());
                record3.setTaskTitle(task.getTitle());
                record3.setTaskDescribe(task.getDescribe());
                record3.setTaskType(task.getType());
                record3.setTaskFollowType(task.getFollowType());
                record3.setTaskRewardType(task.getRewardType());
                record3.setTaskPrice(taskDailyLookVideoToCoin.getPrice());
                record3.setDescribe(TaskFollowTypeEnum.TYPE_8.getMessage());
                record3.setCreateTime(date);
                taskCompleteRecordDao.insertSelective(record3);
            }
        }
    }


    @Override
    @Transactional
    public void adRewardToCoin(Integer userId) {
        Date date = new Date();
        BigDecimal reward = new BigDecimal("100");
        // 加金币
        userDao.addGoldCoin(userId, reward);
        // 添加到金币明细
        billGoldService.addBillGold(userId, BillSceneTypeEnum.SCENE_1.getCode(),
                "看广告奖励", reward, date);
    }


    @Override
    public void shareToCoin(Integer userId) {
        Boolean b = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_9.getCode());
        if (!b) {
            //任务没有开启
            return;
        }
        Date date = new Date();
        //查询当天有没有完成分享领金币任务
        TaskCompleteRecord taskCompleteRecord = taskCompleteRecordDao.
                selectTaskCompleteRecordByTypeDaily(userId, TaskFollowTypeEnum.TYPE_9.getCode());
        if (Objects.isNull(taskCompleteRecord)) {
            TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_9.getCode());
            //加金币，加记录,
            //加金币
            userDao.addGoldCoin(userId, task.getPrice());
            //添加到金币详细中
            billGoldService.addBillGold(userId, BillSceneTypeEnum.SCENE_1.getCode(),
                    TaskFollowTypeEnum.TYPE_9.getMessage(), task.getPrice(), date);
            //添加完成记录
            TaskCompleteRecord record3 = new TaskCompleteRecord();
            record3.setUserId(userId);
            record3.setTaskRewardId(task.getId());
            record3.setTaskTitle(task.getTitle());
            record3.setTaskDescribe(task.getDescribe());
            record3.setTaskType(task.getType());
            record3.setTaskFollowType(task.getFollowType());
            record3.setTaskRewardType(task.getRewardType());
            record3.setTaskPrice(task.getPrice());
            record3.setDescribe(TaskFollowTypeEnum.TYPE_9.getMessage());
            record3.setCreateTime(date);
            taskCompleteRecordDao.insertSelective(record3);
        }
    }


    @Override
    @Transactional
    public void appointDramaCoin(Integer userId, Integer dramaId, Integer second) {
        Boolean b = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_10.getCode());
        if (!b) {
            //任务没有开启
            return;
        }
        //查询当天有没有完成看剧领金币任务
        Date date = new Date();
        TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo = taskDailyAppointDramaCoinInfoDao.
                selectTaskDailyAppointDramaCoinInfoInfo();
        TaskDailyAppointDramaCoinRecord taskDailyAppointDramaCoinRecord = taskDailyAppointDramaCoinRecordDao.
                selectTaskDailyAppointDramaCoinRecordTheSameDay(userId);
        //查看是不是指定的剧？
        TaskDailyAppointDramaCoin taskDailyAppointDramaCoin = taskDailyAppointDramaCoinDao.selectTaskDailyAppointDramaCoinByDramaId(dramaId);
        if (Objects.nonNull(taskDailyAppointDramaCoin)) {
            if (Objects.isNull(taskDailyAppointDramaCoinRecord)) {
                //加秒数
                TaskDailyAppointDramaCoinRecord dailyAppointDramaCoinRecord = new TaskDailyAppointDramaCoinRecord();
                dailyAppointDramaCoinRecord.setUserId(userId);
                dailyAppointDramaCoinRecord.setCreateTime(date);
                dailyAppointDramaCoinRecord.setSecondInfo(second);
                taskDailyAppointDramaCoinRecordDao.insertSelective(dailyAppointDramaCoinRecord);
                TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_10.getCode());
                if (second >= taskDailyAppointDramaCoinInfo.getSecondInfo()) {
                    //加金币，加记录,
                    //加金币
                    TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo1 =
                            taskDailyAppointDramaCoinInfoDao.selectTaskDailyAppointDramaCoinInfoInfo();
                    userDao.addGoldCoin(userId, taskDailyAppointDramaCoinInfo1.getPrice());
                    //添加到金币详细中
                    billGoldService.addBillGold(userId, BillSceneTypeEnum.SCENE_1.getCode(),
                            TaskFollowTypeEnum.TYPE_10.getMessage(), taskDailyAppointDramaCoinInfo1.getPrice(), date);
                    //添加完成记录
                    TaskCompleteRecord record3 = new TaskCompleteRecord();
                    record3.setUserId(userId);
                    record3.setTaskRewardId(task.getId());
                    record3.setTaskTitle(task.getTitle());
                    record3.setTaskDescribe(task.getDescribe());
                    record3.setTaskType(task.getType());
                    record3.setTaskFollowType(task.getFollowType());
                    record3.setTaskRewardType(task.getRewardType());
                    record3.setTaskPrice(taskDailyAppointDramaCoinInfo1.getPrice());
                    record3.setDescribe(TaskFollowTypeEnum.TYPE_10.getMessage());
                    record3.setCreateTime(date);
                    taskCompleteRecordDao.insertSelective(record3);
                }
            } else {
                if (taskDailyAppointDramaCoinInfo.getSecondInfo() > taskDailyAppointDramaCoinRecord.getSecondInfo()) {
                    TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_10.getCode());
                    //加秒数
                    taskDailyAppointDramaCoinRecordDao.addAppointDramaSecondInfo(taskDailyAppointDramaCoinRecord.getId(), second);
                    int sumSecond = taskDailyAppointDramaCoinRecord.getSecondInfo() + second;
                    if (sumSecond >= taskDailyAppointDramaCoinInfo.getSecondInfo()) {
                        TaskDailyAppointDramaCoinInfo taskDailyAppointDramaCoinInfo1 =
                                taskDailyAppointDramaCoinInfoDao.selectTaskDailyAppointDramaCoinInfoInfo();
                        //加金币，加记录,
                        //加金币
                        userDao.addGoldCoin(userId, taskDailyAppointDramaCoinInfo1.getPrice());
                        //添加到金币详细中
                        billGoldService.addBillGold(userId, BillSceneTypeEnum.SCENE_1.getCode(),
                                TaskFollowTypeEnum.TYPE_10.getMessage(), taskDailyAppointDramaCoinInfo1.getPrice(), date);
                        //添加完成记录
                        TaskCompleteRecord record3 = new TaskCompleteRecord();
                        record3.setUserId(userId);
                        record3.setTaskRewardId(task.getId());
                        record3.setTaskTitle(task.getTitle());
                        record3.setTaskDescribe(task.getDescribe());
                        record3.setTaskType(task.getType());
                        record3.setTaskFollowType(task.getFollowType());
                        record3.setTaskRewardType(task.getRewardType());
                        record3.setTaskPrice(taskDailyAppointDramaCoinInfo1.getPrice());
                        record3.setDescribe(TaskFollowTypeEnum.TYPE_10.getMessage());
                        record3.setCreateTime(date);
                        taskCompleteRecordDao.insertSelective(record3);
                    }
                }
            }
        }
    }
}
