package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.AgreementVO;

public interface AgreementService {


    /**
     * 协议信息
     * @param type
     * @return
     */
    AgreementVO getAgreementVO(Integer type);
}
