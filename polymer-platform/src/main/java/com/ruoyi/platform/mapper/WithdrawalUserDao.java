package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.WithdrawalUser;
import com.ruoyi.platform.vo.WithdrawalUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WithdrawalUserDao {

    int deleteByPrimaryKey(Integer id);

    int insert(WithdrawalUser record);

    int insertSelective(WithdrawalUser record);

    WithdrawalUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WithdrawalUser record);

    int updateByPrimaryKey(WithdrawalUser record);

    /**
     * 查看提现绑定用户信息
     */
    WithdrawalUser selectWithdrawalUserByTypeAndUserId(@Param("type") Integer type,
                                                       @Param("userId") Integer userId);

}
