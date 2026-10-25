package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.constant.Constant;
import com.ruoyi.platform.domain.OfficialCode;
import com.ruoyi.platform.mapper.OfficialCodeDao;
import com.ruoyi.platform.service.OfficialCodeService;
import com.ruoyi.platform.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OfficialCodeServiceImpl implements OfficialCodeService {

    @Autowired
    private OfficialCodeDao officialCodeDao;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String getOfficialCode() {
        Object object = redisUtil.get(Constant.OFFICIAL_CODE);
        if (Objects.nonNull(object)) {
            return object.toString();
        } else {
            OfficialCode officialCode = officialCodeDao.selectOfficialCodeInfo();
            redisUtil.set(Constant.OFFICIAL_CODE, officialCode.getContent(), Constant.REDIS_TIME_TEN_DAY, TimeUnit.DAYS);
            return officialCode.getContent();
        }
    }
}
