package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.BillGold;
import com.ruoyi.platform.vo.BillGoldListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BillGoldDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BillGold record);

    int insertSelective(BillGold record);

    BillGold selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BillGold record);

    int updateByPrimaryKey(BillGold record);


    /**
     * 获取我的金币明细
     *
     * @return
     */
    List<BillGoldListVO> selectMyBillGoldListVO(@Param("userId") Integer userId);

    /**
     * 查询今日收益
     */
    BigDecimal selectTodayGetMoney(@Param("userId") Integer userId);
}