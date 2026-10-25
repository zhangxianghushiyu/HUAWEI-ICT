package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.config.exception.ApiException;
import com.ruoyi.platform.constant.Constant;
import com.ruoyi.platform.domain.*;
import com.ruoyi.platform.enums.BillCashTakeEnum;
import com.ruoyi.platform.enums.BillCashTypeEnum;
import com.ruoyi.platform.enums.BillSceneTypeEnum;
import com.ruoyi.platform.enums.TaskFollowTypeEnum;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.BillCashService;
import com.ruoyi.platform.service.BillGoldService;
import com.ruoyi.platform.service.NewPeopleService;
import com.ruoyi.platform.service.TaskRewardService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.RedisUtil;
import com.ruoyi.platform.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class NewPeopleServiceImpl implements NewPeopleService {


    @Autowired
    private TaskRewardDao taskRewardDao;
    @Autowired
    private TaskRewardService taskRewardService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private TaskCompleteRecordDao taskCompleteRecordDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TaskNewPeopleSignDao taskNewPeopleSignDao;
    @Autowired
    private TaskNewPeopleSignRecordDao taskNewPeopleSignRecordDao;
    @Autowired
    private BillGoldService billGoldService;
    @Autowired
    private BillCashService billCashService;
    @Autowired
    private TaskNewPeopleLookDramaDao taskNewPeopleLookDramaDao;
    @Autowired
    private TaskNewPeopleLookDramaRecordDao taskNewPeopleLookDramaRecordDao;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public NewPeopleTaskListVO newPeopleTask() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        NewPeopleTaskListVO newPeopleTaskListVO = new NewPeopleTaskListVO();
        //查询用户有没有完成见面领现金任务
        //查看任务有没有开启
        Boolean b1 = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_1.getCode());
        if (b1) {
            Object object1 = redisUtil.get(Constant.TASK + requestUserId + ":" + TaskFollowTypeEnum.TYPE_1.getCode());
            if (Objects.isNull(object1)) {
                //查询数据库
                TaskCompleteRecord record1 = taskCompleteRecordDao.selectTaskCompleteRecordByTypeNewPeople(requestUserId,
                        TaskFollowTypeEnum.TYPE_1.getCode());
                if (Objects.isNull(record1)) {
                    TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_1.getCode());
                    MeetToReceiveCashVO meetToReceiveCashVO = new MeetToReceiveCashVO();
                    BeanUtils.copyProperties(taskReward, meetToReceiveCashVO);
                    newPeopleTaskListVO.setMeetToReceiveCashVO(meetToReceiveCashVO);
                } else {
                    redisUtil.set(Constant.TASK + requestUserId + ":" + TaskFollowTypeEnum.TYPE_1.getCode(),
                            Constant.REDIS_DEFAULT_VALUE, Constant.REDIS_TIME_TEN_DAY, TimeUnit.DAYS);
                }
            }
        }
        //查询用户有没有完成新人七日签到领金币任务
        //查看任务有没有开启
        Boolean b2 = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_2.getCode());
        if (b2) {
            Object object2 = redisUtil.get(Constant.TASK + requestUserId + ":" + TaskFollowTypeEnum.TYPE_2.getCode());
            if (Objects.isNull(object2)) {
                List<TaskCompleteRecord> taskCompleteRecords2 = taskCompleteRecordDao.selectTaskCompleteRecordByTypeNewPeopleSIgn(requestUserId,
                        TaskFollowTypeEnum.TYPE_2.getCode());
                if (Objects.isNull(taskCompleteRecords2) || taskCompleteRecords2.size() < 7) {
                    TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_2.getCode());
                    NewcomersSignInForSevenDaysVO newcomersSignInForSevenDaysVO = new NewcomersSignInForSevenDaysVO();
                    BeanUtils.copyProperties(taskReward, newcomersSignInForSevenDaysVO);
                    //查看今日有没有签到
                    //查看今天是不是已经签到过？
                    Integer count = taskNewPeopleSignRecordDao.selectLimitTaskNewPeopleSignRecordInfo(requestUserId);
                    if (count > 0) {
                        newcomersSignInForSevenDaysVO.setIsSign(1);
                    }
                    newPeopleTaskListVO.setNewcomersSignInForSevenDaysVO(newcomersSignInForSevenDaysVO);
                    //查询签到的信息集合
                    List<NewcomersSignInForSevenDaysDetailVO> newcomersSignInForSevenDaysDetailVOS = taskNewPeopleSignDao.selectNewcomersSignInForSevenDaysDetailVO(requestUserId);
                    for (NewcomersSignInForSevenDaysDetailVO newcomersSignInForSevenDaysDetailVO : newcomersSignInForSevenDaysDetailVOS) {
                        if (newcomersSignInForSevenDaysDetailVO.getSignRecordId() != null) {
                            newcomersSignInForSevenDaysDetailVO.setIsSign(1);
                        }
                        newcomersSignInForSevenDaysDetailVO.setDayStr(newcomersSignInForSevenDaysDetailVO.getSort() + "天");
                    }
                    newcomersSignInForSevenDaysVO.setNewcomersSignInForSevenDaysDetailVOS(newcomersSignInForSevenDaysDetailVOS);
                } else {
                    redisUtil.set(Constant.TASK + requestUserId + ":" + TaskFollowTypeEnum.TYPE_2.getCode(),
                            Constant.REDIS_DEFAULT_VALUE, Constant.REDIS_TIME_TEN_DAY, TimeUnit.DAYS);
                }
            }
        }
        //查询用户有没有完成新人提现一元任务
        //查看任务有没有开启
        Boolean b3 = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_3.getCode());
        if (b3) {
            Object object3 = redisUtil.get(Constant.TASK + requestUserId + ":" + TaskFollowTypeEnum.TYPE_3.getCode());
            if (Objects.isNull(object3)) {
                TaskCompleteRecord record3 = taskCompleteRecordDao.selectTaskCompleteRecordByTypeNewPeople(requestUserId,
                        TaskFollowTypeEnum.TYPE_3.getCode());
                if (Objects.isNull(record3)) {
                    TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_3.getCode());
                    WithdrawOneYuanVO withdrawOneYuanVO = new WithdrawOneYuanVO();
                    BeanUtils.copyProperties(taskReward, withdrawOneYuanVO);
                    newPeopleTaskListVO.setWithdrawOneYuanVO(withdrawOneYuanVO);
                } else {
                    redisUtil.set(Constant.TASK + requestUserId + ":" + TaskFollowTypeEnum.TYPE_3.getCode(),
                            Constant.REDIS_DEFAULT_VALUE, Constant.REDIS_TIME_TEN_DAY, TimeUnit.DAYS);
                }
            }
        }
        //查看任务有没有开启
        Boolean b4 = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_4.getCode());
        if (b4) {
            //查询用户有没有完成新人看剧领现金任务
            Object object4 = redisUtil.get(Constant.TASK + requestUserId + ":" + TaskFollowTypeEnum.TYPE_4.getCode());
            if (Objects.isNull(object4)) {
                List<TaskCompleteRecord> taskCompleteRecords4 = taskCompleteRecordDao.selectTaskCompleteRecordByTypeNewPeopleSIgn(requestUserId,
                        TaskFollowTypeEnum.TYPE_4.getCode());
                if (Objects.nonNull(taskCompleteRecords4) && taskCompleteRecords4.size() < 3) {
                    TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_4.getCode());
                    NewPeopleLookToDramasCashVO newPeopleLookToDramasCashVO = new NewPeopleLookToDramasCashVO();
                    BeanUtils.copyProperties(taskReward, newPeopleLookToDramasCashVO);
                    newPeopleTaskListVO.setNewPeopleLookToDramasCashVO(newPeopleLookToDramasCashVO);
                    //查询新人看剧送现金的信息集合
                    List<NewPeopleLookDramaDetailVO> newPeopleLookDramaDetailVOS = taskNewPeopleLookDramaDao.
                            selectNewPeopleLookDramaDetailVO(requestUserId);
                    for (NewPeopleLookDramaDetailVO newPeopleLookDramaDetailVO : newPeopleLookDramaDetailVOS) {
                        if (newPeopleLookDramaDetailVO.getReceiveRecordId() != null) {
                            newPeopleLookDramaDetailVO.setIsReceive(1);
                        }
                        newPeopleLookDramaDetailVO.setDayStr("第" + newPeopleLookDramaDetailVO.getSort() + "天");
                    }
                    newPeopleLookToDramasCashVO.setNewPeopleLookDramaDetailVOS(newPeopleLookDramaDetailVOS);
                } else {
                    redisUtil.set(Constant.TASK + requestUserId + ":" + TaskFollowTypeEnum.TYPE_4.getCode(),
                            Constant.REDIS_DEFAULT_VALUE, Constant.REDIS_TIME_TEN_DAY, TimeUnit.DAYS);
                }
            }
        }
        return newPeopleTaskListVO;
    }


    @Override
    @Transactional
    public void meetToReceiveCash() {
        //查看是否开启
        Boolean b = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_1.getCode());
        if (!b){
            throw new ApiException("任务未开启");
        }
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_1.getCode());
        if (Objects.isNull(task)) {
            throw new ApiException("任务信息不存在");
        }
        //查看之前有没有做过此任务
        TaskCompleteRecord taskCompleteRecord = taskCompleteRecordDao.
                selectTaskCompleteRecordByTypeNewPeople(requestUserId, task.getFollowType());
        if (Objects.nonNull(taskCompleteRecord)) {
            throw new ApiException("该任务已经完成");
        }
        Date date = new Date();
        //用户加现金
        userDao.addCash(requestUserId, task.getPrice());
        //添加完成记录
        TaskCompleteRecord record = new TaskCompleteRecord();
        record.setUserId(requestUserId);
        record.setTaskRewardId(task.getId());
        record.setTaskTitle(task.getTitle());
        record.setTaskDescribe(task.getDescribe());
        record.setTaskType(task.getType());
        record.setTaskFollowType(task.getFollowType());
        record.setTaskRewardType(task.getRewardType());
        record.setTaskPrice(task.getPrice());
        record.setDescribe(TaskFollowTypeEnum.TYPE_1.getMessage());
        record.setCreateTime(date);
        taskCompleteRecordDao.insertSelective(record);
        //添加现金明细
        billCashService.addBillCash(null, BillCashTypeEnum.TYPE_1.getCode(), null, requestUserId,
                BillSceneTypeEnum.SCENE_1.getCode(), TaskFollowTypeEnum.TYPE_1.getMessage(), task.getPrice(),
                date, null, null, BillCashTakeEnum.TYPE_1.getCode(),
                null, null,null, "" ,"");
    }


    @Override
    @Transactional
    public void newcomersSignToGoldCoins() {
        Boolean b = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_2.getCode());
        if (!b){
            throw new ApiException("任务未开启");
        }
        Date date = new Date();
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        //查看主任务是不是已经完成
        List<TaskCompleteRecord> taskCompleteRecords = taskCompleteRecordDao.selectTaskCompleteRecordByTypeNewPeopleSIgn(requestUserId,
                TaskFollowTypeEnum.TYPE_2.getCode());
        if (Objects.nonNull(taskCompleteRecords) && taskCompleteRecords.size() >= 7) {
            throw new ApiException("该任务已经完成");
        }
        //查看今天是不是已经签到过？
        Integer count = taskNewPeopleSignRecordDao.selectLimitTaskNewPeopleSignRecordInfo(requestUserId);
        if (count > 0) {
            throw new ApiException("新人签到任务今日已经签到");
        }
        //查询签到的信息集合
        Integer needSignId = null;
        List<NewcomersSignInForSevenDaysDetailVO> newcomersSignInForSevenDaysDetailVOS = taskNewPeopleSignDao.selectNewcomersSignInForSevenDaysDetailVO(requestUserId);
        for (NewcomersSignInForSevenDaysDetailVO newcomersSignInForSevenDaysDetailVO : newcomersSignInForSevenDaysDetailVOS) {
            if (newcomersSignInForSevenDaysDetailVO.getSignRecordId() == null) {
                needSignId = newcomersSignInForSevenDaysDetailVO.getId();
                break;
            }
        }
        if (Objects.isNull(needSignId)) {
            throw new ApiException("没有可签到的信息，签到失败");
        }
        //查询签到信息
        TaskNewPeopleSign taskNewPeopleSign = taskNewPeopleSignDao.selectByPrimaryKey(needSignId);
        //加金币
        userDao.addGoldCoin(requestUserId, taskNewPeopleSign.getPrice());
        //添加一条记录
        TaskNewPeopleSignRecord record = new TaskNewPeopleSignRecord();
        record.setUserId(requestUserId);
        record.setPrice(taskNewPeopleSign.getPrice());
        record.setCreateTime(date);
        record.setNewPeopleSignId(taskNewPeopleSign.getId());
        taskNewPeopleSignRecordDao.insertSelective(record);
        //添加到金币详细中
        billGoldService.addBillGold(requestUserId, BillSceneTypeEnum.SCENE_1.getCode(),
                TaskFollowTypeEnum.TYPE_2.getMessage(), taskNewPeopleSign.getPrice(), date);
        TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_2.getCode());
        //已经签到完成
        //添加完成记录
        TaskCompleteRecord record3 = new TaskCompleteRecord();
        record3.setUserId(requestUserId);
        record3.setTaskRewardId(task.getId());
        record3.setTaskTitle(task.getTitle());
        record3.setTaskDescribe(task.getDescribe());
        record3.setTaskType(task.getType());
        record3.setTaskFollowType(task.getFollowType());
        record3.setTaskRewardType(task.getRewardType());
        record3.setTaskPrice(taskNewPeopleSign.getPrice());
        record3.setDescribe(TaskFollowTypeEnum.TYPE_2.getMessage());
        record3.setCreateTime(date);
        taskCompleteRecordDao.insertSelective(record3);
    }


    @Override
    public void withdrawOneYuan(Integer userId) {
        Boolean b = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_3.getCode());
        if (!b){
            //任务没有开启
            return;
        }
        TaskCompleteRecord record3 = taskCompleteRecordDao.selectTaskCompleteRecordByTypeNewPeople(userId,
                TaskFollowTypeEnum.TYPE_3.getCode());
        if (Objects.isNull(record3)) {
            Date date = new Date();
            TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_3.getCode());
            TaskCompleteRecord record = new TaskCompleteRecord();
            record.setUserId(userId);
            record.setTaskRewardId(taskReward.getId());
            record.setTaskTitle(taskReward.getTitle());
            record.setTaskDescribe(taskReward.getDescribe());
            record.setTaskType(taskReward.getType());
            record.setTaskFollowType(taskReward.getFollowType());
            record.setTaskRewardType(taskReward.getRewardType());
//            record.setTaskPrice(taskReward.getPrice());
            record.setDescribe(TaskFollowTypeEnum.TYPE_3.getMessage());
            record.setCreateTime(date);
            taskCompleteRecordDao.insertSelective(record);
        }
    }


    @Override
    @Transactional
    public synchronized void newPeopleLookToDramasCash(Integer userId) {
        try {
            Boolean b = taskRewardService.checkIsOpen(TaskFollowTypeEnum.TYPE_4.getCode());
            if (!b){
                //任务没有开启
                return;
            }
            //查看今天是不是已经签到过？
            Integer count = taskNewPeopleLookDramaRecordDao.selectLimitTaskNewPeopleLookDramaRecordInfo(userId);
            if (count <= 0) {
                List<TaskCompleteRecord> taskCompleteRecords4 = taskCompleteRecordDao.selectTaskCompleteRecordByTypeNewPeopleSIgn(userId,
                        TaskFollowTypeEnum.TYPE_4.getCode());
                if (Objects.nonNull(taskCompleteRecords4) && taskCompleteRecords4.size() < 3) {
                    TaskReward task = taskRewardDao.selectByPrimaryFollowType(TaskFollowTypeEnum.TYPE_4.getCode());
                    //查询新人看剧送现金的信息集合
                    List<NewPeopleLookDramaDetailVO> newPeopleLookDramaDetailVOS = taskNewPeopleLookDramaDao.
                            selectNewPeopleLookDramaDetailVO(userId);
                    Integer needReceiveId = null;
                    for (NewPeopleLookDramaDetailVO newPeopleLookDramaDetailVO : newPeopleLookDramaDetailVOS) {
                        if (newPeopleLookDramaDetailVO.getReceiveRecordId() == null) {
                            needReceiveId = newPeopleLookDramaDetailVO.getId();
                            break;
                        }
                    }
                    TaskNewPeopleLookDrama taskNewPeopleLookDrama = taskNewPeopleLookDramaDao.
                            selectByPrimaryKey(needReceiveId);
                    //实现任务
                    Date date = new Date();
                    //用户加现金
                    userDao.addCash(userId, taskNewPeopleLookDrama.getPrice());
                    //添加完成记录
                    TaskCompleteRecord record = new TaskCompleteRecord();
                    record.setUserId(userId);
                    record.setTaskRewardId(task.getId());
                    record.setTaskTitle(task.getTitle());
                    record.setTaskDescribe(task.getDescribe());
                    record.setTaskType(task.getType());
                    record.setTaskFollowType(task.getFollowType());
                    record.setTaskRewardType(task.getRewardType());
                    record.setTaskPrice(taskNewPeopleLookDrama.getPrice());
                    record.setDescribe(TaskFollowTypeEnum.TYPE_4.getMessage());
                    record.setCreateTime(date);
                    taskCompleteRecordDao.insertSelective(record);
                    //添加现金明细
                    billCashService.addBillCash(null, BillCashTypeEnum.TYPE_1.getCode(), null, userId,
                            BillSceneTypeEnum.SCENE_1.getCode(), TaskFollowTypeEnum.TYPE_4.getMessage(), taskNewPeopleLookDrama.getPrice(),
                            date, null, null, BillCashTakeEnum.TYPE_1.getCode(),
                            null, null,null, "", "");
                    //添加一条记录
                    TaskNewPeopleLookDramaRecord record2 = new TaskNewPeopleLookDramaRecord();
                    record2.setUserId(userId);
                    record2.setPrice(taskNewPeopleLookDrama.getPrice());
                    record2.setCreateTime(date);
                    record2.setNewPeopleLookDramaId(taskNewPeopleLookDrama.getId());
                    taskNewPeopleLookDramaRecordDao.insertSelective(record2);
                }
            }
        }catch (Exception e){
            log.error("新人看剧领现金时出现异常{}", e.getMessage());
        }
    }
}
