package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.DailyTaskListVO;

import java.util.Date;


public interface DailyService {


    /**
     * 每日任务
     */
    DailyTaskListVO daily();


    /**
     * 每日七日签到
     */
    void dailySignToGoldCoins();


    /**
     * 饭点看剧领补贴
     */
    void foodPointLookDramaToGoldCoins(Integer userId);


    /**
     * 看剧领金币
     */
    void lookDramaToCoin(Integer userId, Integer second);


    /**
     * 看小视频领金币
     */
    void lookVideoToCoin(Integer userId, Date date);


    /**
     * 看剧领金币
     */
    void shareToCoin(Integer userId);

    /**
     * 看广告领金币
     */
    void adRewardToCoin(Integer userId);

    /**
     * 看指定剧领金币
     */
    void appointDramaCoin(Integer userId,Integer dramaId,Integer second);

}
