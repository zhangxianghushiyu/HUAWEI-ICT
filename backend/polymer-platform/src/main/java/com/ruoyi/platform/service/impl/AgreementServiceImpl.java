package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.mapper.AgreementDao;
import com.ruoyi.platform.service.AgreementService;
import com.ruoyi.platform.vo.AgreementVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    private AgreementDao agreementDao;

    @Override
    public AgreementVO getAgreementVO(Integer type) {
        return agreementDao.selectAgreementVO(type);
    }
}
