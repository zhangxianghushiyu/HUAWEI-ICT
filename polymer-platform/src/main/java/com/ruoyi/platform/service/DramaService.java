package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.DramaHomePageVO;

import java.util.List;

public interface DramaService {


    /**
     * 首页查询
     *
     * @return
     */
    List<DramaHomePageVO> homePage(Integer mainType,Integer categoryId);

    /**
     * 首页搜索
     *
     * @return
     */
    List<DramaHomePageVO> query(String title);

    /**
     * 金币解锁短剧
     */
    void moneyUnlock(Integer dramaId);

}
