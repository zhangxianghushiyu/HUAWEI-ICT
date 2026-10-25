package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.domain.ApplyDestroy;
import com.ruoyi.platform.mapper.ApplyDestroyDao;
import com.ruoyi.platform.service.ApplyDestroyService;
import com.ruoyi.platform.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class ApplyDestroyServiceImpl implements ApplyDestroyService {

    @Autowired
    private ApplyDestroyDao applyDestroyDao;
    @Autowired
    private HttpServletRequest request;

    @Override
    public void userApplyDestroy() {
        Integer requestUserId = JwtUtil.getRequestUserId(request);
        ApplyDestroy applyDestroy=new ApplyDestroy();
        applyDestroy.setCreateTime(new Date());
        applyDestroy.setUserId(requestUserId);
        applyDestroyDao.insertSelective(applyDestroy);
    }
}
