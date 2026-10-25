package com.ruoyi.platform.service;

import com.ruoyi.platform.dto.TaKuAdCallbackDTO;
import com.ruoyi.platform.vo.TaKuAdCallbackVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface TaKuCallbackService {



    /**
     * taku
     *
     * @param request
     */
    Integer taKuCallback(HttpServletRequest request, TaKuAdCallbackDTO dto);

    /**
     * 查询结果
     */
    TaKuAdCallbackVO getTaKuAdCallbackVO(String transId);

    /**
     * 验证是否可以查看广告
     */
    void checkIsLookAd();

    Integer lookAdCount();

    Map checkIsLookMakeMoneyAd();
}
