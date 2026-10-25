package com.ruoyi.platform.service;

import java.util.Date;

public interface UserLoginLogService {

    /**
     * 添加用户登录日志
     */
    void addUserLoginLogInfo(Integer userId, Date date);


    /**
     * 添加用户登录日志
     */
    void addUserLoginLog();
}
