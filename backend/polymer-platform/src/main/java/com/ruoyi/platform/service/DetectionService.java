package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.Detection;

import java.util.Date;

public interface DetectionService {



    /**
     * 风控处理
     * 返回false表示已经被停用
     */
    Boolean handleDetection3(Detection detection, Integer userId, Date date);


    /**
     * 风控处理
     * 返回false表示已经被停用
     */
    Boolean handleDetection4(Detection detection,Integer userId, Date date, String publisherRevenue);


    /**
     * 风控处理
     * 返回false表示已经被停用
     */
    Boolean handleDetection5(Detection detection,Integer userId, Date date, String publisherRevenue);


    /**
     * 风控处理
     * 返回false表示已经被停用
     */
    Boolean handleDetection6(Detection detection,Integer userId, Date date, String publisherRevenue);


    /**
     * 风控处理
     * 返回false表示已经被停用
     */
    Boolean handleDetection7(Detection detection,Integer userId, Date date, String publisherRevenue);
}
