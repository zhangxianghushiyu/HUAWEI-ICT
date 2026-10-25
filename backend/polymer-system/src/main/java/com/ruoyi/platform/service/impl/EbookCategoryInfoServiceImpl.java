package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.EbookCategoryInfoMapper;
import com.ruoyi.platform.domain.EbookCategoryInfo;
import com.ruoyi.platform.service.IEbookCategoryInfoService;

/**
 * 分类及电子书关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
@Service
public class EbookCategoryInfoServiceImpl implements IEbookCategoryInfoService 
{
    @Autowired
    private EbookCategoryInfoMapper ebookCategoryInfoMapper;

    /**
     * 查询分类及电子书关联
     * 
     * @param id 分类及电子书关联主键
     * @return 分类及电子书关联
     */
    @Override
    public EbookCategoryInfo selectEbookCategoryInfoById(Long id)
    {
        return ebookCategoryInfoMapper.selectEbookCategoryInfoById(id);
    }

    /**
     * 查询分类及电子书关联列表
     * 
     * @param ebookCategoryInfo 分类及电子书关联
     * @return 分类及电子书关联
     */
    @Override
    public List<EbookCategoryInfo> selectEbookCategoryInfoList(EbookCategoryInfo ebookCategoryInfo)
    {
        return ebookCategoryInfoMapper.selectEbookCategoryInfoList(ebookCategoryInfo);
    }

    /**
     * 新增分类及电子书关联
     * 
     * @param ebookCategoryInfo 分类及电子书关联
     * @return 结果
     */
    @Override
    public int insertEbookCategoryInfo(EbookCategoryInfo ebookCategoryInfo)
    {
        ebookCategoryInfo.setCreateTime(DateUtils.getNowDate());
        return ebookCategoryInfoMapper.insertEbookCategoryInfo(ebookCategoryInfo);
    }

    /**
     * 修改分类及电子书关联
     * 
     * @param ebookCategoryInfo 分类及电子书关联
     * @return 结果
     */
    @Override
    public int updateEbookCategoryInfo(EbookCategoryInfo ebookCategoryInfo)
    {
        ebookCategoryInfo.setUpdateTime(DateUtils.getNowDate());
        return ebookCategoryInfoMapper.updateEbookCategoryInfo(ebookCategoryInfo);
    }

    /**
     * 批量删除分类及电子书关联
     * 
     * @param ids 需要删除的分类及电子书关联主键
     * @return 结果
     */
    @Override
    public int deleteEbookCategoryInfoByIds(Long[] ids)
    {
        return ebookCategoryInfoMapper.deleteEbookCategoryInfoByIds(ids);
    }

    /**
     * 删除分类及电子书关联信息
     * 
     * @param id 分类及电子书关联主键
     * @return 结果
     */
    @Override
    public int deleteEbookCategoryInfoById(Long id)
    {
        return ebookCategoryInfoMapper.deleteEbookCategoryInfoById(id);
    }
}
