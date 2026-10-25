package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.EbookCategoryMapper;
import com.ruoyi.platform.domain.EbookCategory;
import com.ruoyi.platform.service.IEbookCategoryService;

/**
 * 电子书分类Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
@Service
public class EbookCategoryServiceImpl implements IEbookCategoryService 
{
    @Autowired
    private EbookCategoryMapper ebookCategoryMapper;

    /**
     * 查询电子书分类
     * 
     * @param id 电子书分类主键
     * @return 电子书分类
     */
    @Override
    public EbookCategory selectEbookCategoryById(Long id)
    {
        return ebookCategoryMapper.selectEbookCategoryById(id);
    }

    /**
     * 查询电子书分类列表
     * 
     * @param ebookCategory 电子书分类
     * @return 电子书分类
     */
    @Override
    public List<EbookCategory> selectEbookCategoryList(EbookCategory ebookCategory)
    {
        return ebookCategoryMapper.selectEbookCategoryList(ebookCategory);
    }

    /**
     * 新增电子书分类
     * 
     * @param ebookCategory 电子书分类
     * @return 结果
     */
    @Override
    public int insertEbookCategory(EbookCategory ebookCategory)
    {
        ebookCategory.setCreateTime(DateUtils.getNowDate());
        return ebookCategoryMapper.insertEbookCategory(ebookCategory);
    }

    /**
     * 修改电子书分类
     * 
     * @param ebookCategory 电子书分类
     * @return 结果
     */
    @Override
    public int updateEbookCategory(EbookCategory ebookCategory)
    {
        ebookCategory.setUpdateTime(DateUtils.getNowDate());
        return ebookCategoryMapper.updateEbookCategory(ebookCategory);
    }

    /**
     * 批量删除电子书分类
     * 
     * @param ids 需要删除的电子书分类主键
     * @return 结果
     */
    @Override
    public int deleteEbookCategoryByIds(Long[] ids)
    {
        return ebookCategoryMapper.deleteEbookCategoryByIds(ids);
    }

    /**
     * 删除电子书分类信息
     * 
     * @param id 电子书分类主键
     * @return 结果
     */
    @Override
    public int deleteEbookCategoryById(Long id)
    {
        return ebookCategoryMapper.deleteEbookCategoryById(id);
    }
}
