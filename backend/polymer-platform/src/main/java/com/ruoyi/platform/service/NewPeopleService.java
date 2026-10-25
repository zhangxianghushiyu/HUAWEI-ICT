package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.NewPeopleTaskListVO;

public interface NewPeopleService {

    /**
     * 新人任务
     */
    NewPeopleTaskListVO newPeopleTask();


    /**
     * 见面领金币
     */
    void meetToReceiveCash();


    /**
     * 新人七日签到
     */
    void newcomersSignToGoldCoins();

    /**
     * 提现一元
     */
    void withdrawOneYuan(Integer userId);

    /**
     * 新人看剧领现金
     */
    void newPeopleLookToDramasCash(Integer userId);
}
