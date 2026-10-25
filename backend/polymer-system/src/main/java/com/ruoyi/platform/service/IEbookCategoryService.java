package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.EbookCategory;

/**
 * 电子书分类Service接口
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
public interface IEbookCategoryService 
{
    /**
     * 查询电子书分类
     * 
     * @param id 电子书分类主键
     * @return 电子书分类
     */
    public EbookCategory selectEbookCategoryById(Long id);

    /**
     * 查询电子书分类列表
     * 
     * @param ebookCategory 电子书分类
     * @return 电子书分类集合
     */
    public List<EbookCategory> selectEbookCategoryList(EbookCategory ebookCategory);

    /**
     * 新增电子书分类
     * 
     * @param ebookCategory 电子书分类
     * @return 结果
     */
    public int insertEbookCategory(EbookCategory ebookCategory);

    /**
     * 修改电子书分类
     * 
     * @param ebookCategory 电子书分类
     * @return 结果
     */
    public int updateEbookCategory(EbookCategory ebookCategory);

    /**
     * 批量删除电子书分类
     * 
     * @param ids 需要删除的电子书分类主键集合
     * @return 结果
     */
    public int deleteEbookCategoryByIds(Long[] ids);

    /**
     * 删除电子书分类信息
     * 
     * @param id 电子书分类主键
     * @return 结果
     */
    public int deleteEbookCategoryById(Long id);
}
