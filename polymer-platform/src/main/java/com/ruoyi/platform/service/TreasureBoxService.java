package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.TreasureBoxVO;

import java.math.BigDecimal;
import java.util.Date;

public interface TreasureBoxService {

    /**
     * 获得宝箱信息
     */
    TreasureBoxVO get();

    /**
     * 验证是否可以点击宝箱
     */
    BigDecimal receive();

    /**
     * 获取宝箱金额
     */
    void obtain(Integer userId, Date date, BigDecimal money);
}
