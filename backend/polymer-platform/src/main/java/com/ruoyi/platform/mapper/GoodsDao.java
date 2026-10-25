package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.Goods;
import com.ruoyi.platform.domain.GoodsType;
import com.ruoyi.platform.vo.GoodsListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<GoodsListVO> selectGoodsListVO(@Param("goodsType") Integer goodsType, @Param("goodsName") String goodsName);

    List<GoodsType> getGoodsTypeList();
}