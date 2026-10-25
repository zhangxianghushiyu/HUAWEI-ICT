package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.mapper.VersionDao;
import com.ruoyi.platform.service.ConvertRatioService;
import com.ruoyi.platform.service.VersionService;
import com.ruoyi.platform.vo.VersionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionServiceImpl implements VersionService {


    @Autowired
    private VersionDao versionDao;
    @Autowired
    private ConvertRatioService convertRatioService;

    @Override
    public VersionVO selectVersionVO() {
        VersionVO versionVO = versionDao.selectVersionVO();
        Integer convertRatio = convertRatioService.getConvertRatio();
        versionVO.setConvertRatio(convertRatio);
        return versionVO;
    }
}
