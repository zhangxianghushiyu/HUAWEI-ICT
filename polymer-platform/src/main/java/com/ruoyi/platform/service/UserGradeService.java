package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.User;

import java.util.Date;

public interface UserGradeService {

    /**
     * 处理用户升级
     */
    void handleUserGrade(User user, Date date);
}
