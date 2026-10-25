package com.ruoyi.platform.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.GoodsType;
import com.ruoyi.platform.mapper.GoodsTypeMapper;
import com.ruoyi.platform.service.IGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类Service业务层处理
 * 
 * @author yq
 * @date 2025-09-01
 */
@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService 
{
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    /**
     * 查询商品分类
     * 
     * @param id 商品分类主键
     * @return 商品分类
     */
    @Override
    public GoodsType selectGoodsTypeById(Integer id)
    {
        return goodsTypeMapper.selectGoodsTypeById(id);
    }

    /**
     * 查询商品分类列表
     * 
     * @param goodsType 商品分类
     * @return 商品分类
     */
    @Override
    public List<GoodsType> selectGoodsTypeList(GoodsType goodsType)
    {
        return goodsTypeMapper.selectGoodsTypeList(goodsType);
    }

    /**
     * 新增商品分类
     * 
     * @param goodsType 商品分类
     * @return 结果
     */
    @Override
    public int insertGoodsType(GoodsType goodsType)
    {
        goodsType.setCreateTime(DateUtils.getNowDate());
        return goodsTypeMapper.insertGoodsType(goodsType);
    }

    /**
     * 修改商品分类
     * 
     * @param goodsType 商品分类
     * @return 结果
     */
    @Override
    public int updateGoodsType(GoodsType goodsType)
    {
        return goodsTypeMapper.updateGoodsType(goodsType);
    }

    /**
     * 批量删除商品分类
     * 
     * @param ids 需要删除的商品分类主键
     * @return 结果
     */
    @Override
    public int deleteGoodsTypeByIds(Integer[] ids)
    {
        return goodsTypeMapper.deleteGoodsTypeByIds(ids);
    }

    /**
     * 删除商品分类信息
     * 
     * @param id 商品分类主键
     * @return 结果
     */
    @Override
    public int deleteGoodsTypeById(Integer id)
    {
        return goodsTypeMapper.deleteGoodsTypeById(id);
    }
}
