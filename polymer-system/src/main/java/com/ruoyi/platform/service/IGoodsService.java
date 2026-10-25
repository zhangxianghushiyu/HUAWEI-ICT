package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.Goods;

import java.util.List;

/**
 * 商品信息Service接口
 * 
 * @author ruoyi
 * @date 2025-03-14
 */
public interface IGoodsService 
{
    /**
     * 查询商品信息
     * 
     * @param id 商品信息主键
     * @return 商品信息
     */
    public Goods selectGoodsById(Long id);

    /**
     * 查询商品信息列表
     * 
     * @param goods 商品信息
     * @return 商品信息集合
     */
    public List<Goods> selectGoodsList(Goods goods);

    /**
     * 新增商品信息
     * 
     * @param goods 商品信息
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 修改商品信息
     * 
     * @param goods 商品信息
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 批量删除商品信息
     * 
     * @param ids 需要删除的商品信息主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);

    /**
     * 删除商品信息信息
     * 
     * @param id 商品信息主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);

    /**
     * 批量下架短剧
     *
     * @param ids 需要删除的视频主键集合
     * @return 结果
     */
    public int undercarriage(Long[] ids);

    /**
     * 批量上架短剧
     *
     * @param ids 需要删除的视频主键集合
     * @return 结果
     */
    public int grounding(Long[] ids);
}
