package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.WithdrawalInterval;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WithdrawalIntervalDao {
    int deleteByPrimaryKey(Integer id);

    int insert(WithdrawalInterval record);

    int insertSelective(WithdrawalInterval record);

    WithdrawalInterval selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WithdrawalInterval record);

    int updateByPrimaryKey(WithdrawalInterval record);

    /**
     * 当天设置的提现次数
     */
    WithdrawalInterval selectWithdrawalIntervalInfo();
}