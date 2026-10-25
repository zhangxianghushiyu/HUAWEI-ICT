package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.EbookCategoryInfo;

/**
 * 分类及电子书关联Service接口
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
public interface IEbookCategoryInfoService 
{
    /**
     * 查询分类及电子书关联
     * 
     * @param id 分类及电子书关联主键
     * @return 分类及电子书关联
     */
    public EbookCategoryInfo selectEbookCategoryInfoById(Long id);

    /**
     * 查询分类及电子书关联列表
     * 
     * @param ebookCategoryInfo 分类及电子书关联
     * @return 分类及电子书关联集合
     */
    public List<EbookCategoryInfo> selectEbookCategoryInfoList(EbookCategoryInfo ebookCategoryInfo);

    /**
     * 新增分类及电子书关联
     * 
     * @param ebookCategoryInfo 分类及电子书关联
     * @return 结果
     */
    public int insertEbookCategoryInfo(EbookCategoryInfo ebookCategoryInfo);

    /**
     * 修改分类及电子书关联
     * 
     * @param ebookCategoryInfo 分类及电子书关联
     * @return 结果
     */
    public int updateEbookCategoryInfo(EbookCategoryInfo ebookCategoryInfo);

    /**
     * 批量删除分类及电子书关联
     * 
     * @param ids 需要删除的分类及电子书关联主键集合
     * @return 结果
     */
    public int deleteEbookCategoryInfoByIds(Long[] ids);

    /**
     * 删除分类及电子书关联信息
     * 
     * @param id 分类及电子书关联主键
     * @return 结果
     */
    public int deleteEbookCategoryInfoById(Long id);
}
