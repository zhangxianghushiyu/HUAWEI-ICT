package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.GoodsOrder;
import com.ruoyi.platform.vo.GoodsOrderListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsOrderDao {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsOrder record);

    int insertSelective(GoodsOrder record);

    GoodsOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsOrder record);

    int updateByPrimaryKey(GoodsOrder record);



    /**
     * 获取订单信息
     */
    List<GoodsOrderListVO> selectGoodsOrderListVO(@Param("userId")Integer userId);
}