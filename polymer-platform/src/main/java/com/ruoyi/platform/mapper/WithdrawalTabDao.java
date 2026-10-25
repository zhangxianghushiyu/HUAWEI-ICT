package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.WithdrawalTab;
import com.ruoyi.platform.vo.WithdrawalTabVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WithdrawalTabDao {
    int deleteByPrimaryKey(Integer id);

    int insert(WithdrawalTab record);

    int insertSelective(WithdrawalTab record);

    WithdrawalTab selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WithdrawalTab record);

    int updateByPrimaryKey(WithdrawalTab record);


    /**
     * 获取提现选项卡信息
     *
     * @return
     */
    List<WithdrawalTabVO> selectWithdrawalTabVOInfo(@Param("type") Integer type);
}