package com.ruoyi.platform.service.impl;

import cn.hutool.extra.servlet.ServletUtil;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.domain.UserLoginLog;
import com.ruoyi.platform.mapper.UserDao;
import com.ruoyi.platform.mapper.UserLoginLogDao;
import com.ruoyi.platform.service.UserLoginLogService;
import com.ruoyi.platform.utils.JwtUtil;
import com.ruoyi.platform.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class UserLoginLogServiceImpl implements UserLoginLogService {

    @Autowired
    private UserLoginLogDao userLoginLogDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDao userDao;


    @Override
    public void addUserLoginLogInfo(Integer userId, Date date) {
        try {
            //处理登录日志
            String clientIP = ServletUtil.getClientIP(ServletUtils.getRequest(), null);
            UserLoginLog userLoginLog = new UserLoginLog();
            userLoginLog.setUserId(userId);
            userLoginLog.setIpAddr(StringUtils.isNotEmpty(clientIP) ? clientIP : null);
            userLoginLog.setCreateTime(date);
            userLoginLogDao.insertSelective(userLoginLog);
            //修改用户最后一次登录时间
            User user = userDao.selectByPrimaryKey(userId);
            if (Objects.nonNull(user)) {
                user.setLastIp(StringUtils.isNotEmpty(clientIP) ? clientIP : null);
                userDao.updateByPrimaryKeySelective(user);
            }
        } catch (Exception e) {
            log.error("登录日志信息错误", e);
        }
    }

    @Override
    public void addUserLoginLog() {
        try {
            Integer requestUserId = JwtUtil.getRequestUserId(request);
            if (Objects.nonNull(requestUserId)) {
                Date date = new Date();
                //处理登录日志
                String clientIP = ServletUtil.getClientIP(ServletUtils.getRequest(), null);
                UserLoginLog userLoginLog = new UserLoginLog();
                userLoginLog.setUserId(requestUserId);
                userLoginLog.setIpAddr(StringUtils.isNotEmpty(clientIP) ? clientIP : null);
                userLoginLog.setCreateTime(date);
                userLoginLogDao.insertSelective(userLoginLog);
                //修改用户最后一次登录时间
                User user = userDao.selectByPrimaryKey(requestUserId);
                if (Objects.nonNull(user)) {
                    user.setLastIp(StringUtils.isNotEmpty(clientIP) ? clientIP : null);
                    userDao.updateByPrimaryKeySelective(user);
                }
            }
        } catch (Exception e) {
            log.error("登录日志信息错误", e);
        }
    }
}
