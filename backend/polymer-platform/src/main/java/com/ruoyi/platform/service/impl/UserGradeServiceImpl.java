package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.domain.GradeConfig;
import com.ruoyi.platform.domain.User;
import com.ruoyi.platform.domain.UserGrade;
import com.ruoyi.platform.mapper.GradeConfigDao;
import com.ruoyi.platform.mapper.UserDao;
import com.ruoyi.platform.mapper.UserGradeDao;
import com.ruoyi.platform.service.UserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserGradeServiceImpl implements UserGradeService {

    @Autowired
    private UserGradeDao userGradeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GradeConfigDao gradeConfigDao;

    @Override
    public void handleUserGrade(User user, Date date) {
//查询用户当前等级已经完成了多少关？
        UserGrade userGrade = userGradeDao.selectUserGradeMax(user.getId().longValue());
        //完成数量加1
        int num = userGrade.getBarrier() + 1;
        //查询配置的升级需要通关数量
        GradeConfig gradeConfig = gradeConfigDao.selectGradeConfig(userGrade.getGrade());
        if (num >= gradeConfig.getBarrier()) {
            //升级
            user.setGrade(user.getGrade() + 1);
            userDao.updateByPrimaryKeySelective(user);
            //修改数据，并且新生成一条数据做为初始化下一级的数据
            userGrade.setBarrier(userGrade.getBarrier() + 1);
            userGradeDao.updateByPrimaryKeySelective(userGrade);
            //新增一条下一个等级需要的升级数据
            UserGrade userGrade1 = new UserGrade();
            userGrade1.setUserId(user.getId());
            userGrade1.setGrade(userGrade.getGrade() + 1);
            userGrade1.setBarrier(0);
            userGrade1.setCreateTime(date);
            userGradeDao.insertSelective(userGrade1);
        } else {
            //不升级
            userGrade.setBarrier(userGrade.getBarrier() + 1);
            userGradeDao.updateByPrimaryKeySelective(userGrade);
        }
    }
}
