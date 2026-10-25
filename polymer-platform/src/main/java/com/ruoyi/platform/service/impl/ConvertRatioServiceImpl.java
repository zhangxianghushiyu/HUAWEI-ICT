package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.constant.Constant;
import com.ruoyi.platform.mapper.ConvertRatioDao;
import com.ruoyi.platform.service.ConvertRatioService;
import com.ruoyi.platform.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ConvertRatioServiceImpl implements ConvertRatioService {

    @Autowired
    private ConvertRatioDao convertRatioDao;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Integer getConvertRatio() {
        Object object = redisUtil.get(Constant.CONVERT_RATIO);
        if (Objects.nonNull(object)){
            return Integer.parseInt(object.toString());
        }else{
            Integer ratio = convertRatioDao.selectConvertRatioInfo().getRatio();
            redisUtil.set(Constant.CONVERT_RATIO,ratio,Constant.REDIS_TIME_TEN_DAY, TimeUnit.DAYS);
            return ratio;
        }
    }
}
