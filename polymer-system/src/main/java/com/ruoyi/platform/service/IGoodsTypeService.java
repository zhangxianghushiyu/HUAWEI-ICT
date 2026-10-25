package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.GoodsType;

import java.util.List;

/**
 * 商品分类Service接口
 * 
 * @author yq
 * @date 2025-09-01
 */
public interface IGoodsTypeService 
{
    /**
     * 查询商品分类
     * 
     * @param id 商品分类主键
     * @return 商品分类
     */
    public GoodsType selectGoodsTypeById(Integer id);

    /**
     * 查询商品分类列表
     * 
     * @param goodsType 商品分类
     * @return 商品分类集合
     */
    public List<GoodsType> selectGoodsTypeList(GoodsType goodsType);

    /**
     * 新增商品分类
     * 
     * @param goodsType 商品分类
     * @return 结果
     */
    public int insertGoodsType(GoodsType goodsType);

    /**
     * 修改商品分类
     * 
     * @param goodsType 商品分类
     * @return 结果
     */
    public int updateGoodsType(GoodsType goodsType);

    /**
     * 批量删除商品分类
     * 
     * @param ids 需要删除的商品分类主键集合
     * @return 结果
     */
    public int deleteGoodsTypeByIds(Integer[] ids);

    /**
     * 删除商品分类信息
     * 
     * @param id 商品分类主键
     * @return 结果
     */
    public int deleteGoodsTypeById(Integer id);
}
