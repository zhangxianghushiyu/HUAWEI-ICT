package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.constant.Constant;
import com.ruoyi.platform.domain.TaskReward;
import com.ruoyi.platform.mapper.TaskRewardDao;
import com.ruoyi.platform.service.TaskRewardService;
import com.ruoyi.platform.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TaskRewardServiceImpl implements TaskRewardService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private TaskRewardDao taskRewardDao;

    @Override
    public Boolean checkIsOpen(Integer followType) {
        Object object = redisUtil.get(Constant.TASK_STATUS + followType);
        if (Objects.nonNull(object)) {
            int num = Integer.parseInt(object.toString());
            return num == 1;
        } else {
            //查询数据库
            TaskReward taskReward = taskRewardDao.selectByPrimaryFollowType(followType);
            redisUtil.set(Constant.TASK_STATUS + followType, taskReward.getStatus(),
                    Constant.REDIS_TIME_TEN_DAY, TimeUnit.DAYS);
            return taskReward.getStatus() == 1;
        }
    }
}
