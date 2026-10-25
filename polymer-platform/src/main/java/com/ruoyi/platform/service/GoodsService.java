package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.GoodsType;
import com.ruoyi.platform.dto.GoodsGetDTO;
import com.ruoyi.platform.vo.GoodsListVO;

import java.util.List;

public interface GoodsService {


    /**
     * 获取商品信息集合
     */
    List<GoodsListVO> getGoodsListVO(Integer goodsType, String goodsName);


    /**
     * 购买商品
     */
    void buy(GoodsGetDTO dto);

    List<GoodsType> getGoodsTypeList();
}
