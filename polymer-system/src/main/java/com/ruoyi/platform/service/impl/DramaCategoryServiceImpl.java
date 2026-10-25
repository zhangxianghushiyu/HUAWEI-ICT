package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.DramaCategoryMapper;
import com.ruoyi.platform.domain.DramaCategory;
import com.ruoyi.platform.service.IDramaCategoryService;

/**
 * 短剧分类Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
@Service
public class DramaCategoryServiceImpl implements IDramaCategoryService 
{
    @Autowired
    private DramaCategoryMapper dramaCategoryMapper;

    /**
     * 查询短剧分类
     * 
     * @param id 短剧分类主键
     * @return 短剧分类
     */
    @Override
    public DramaCategory selectDramaCategoryById(Long id)
    {
        return dramaCategoryMapper.selectDramaCategoryById(id);
    }

    /**
     * 查询短剧分类列表
     * 
     * @param dramaCategory 短剧分类
     * @return 短剧分类
     */
    @Override
    public List<DramaCategory> selectDramaCategoryList(DramaCategory dramaCategory)
    {
        return dramaCategoryMapper.selectDramaCategoryList(dramaCategory);
    }

    /**
     * 新增短剧分类
     * 
     * @param dramaCategory 短剧分类
     * @return 结果
     */
    @Override
    public int insertDramaCategory(DramaCategory dramaCategory)
    {
        dramaCategory.setCreateTime(DateUtils.getNowDate());
        return dramaCategoryMapper.insertDramaCategory(dramaCategory);
    }

    /**
     * 修改短剧分类
     * 
     * @param dramaCategory 短剧分类
     * @return 结果
     */
    @Override
    public int updateDramaCategory(DramaCategory dramaCategory)
    {
        dramaCategory.setUpdateTime(DateUtils.getNowDate());
        return dramaCategoryMapper.updateDramaCategory(dramaCategory);
    }

    /**
     * 批量删除短剧分类
     * 
     * @param ids 需要删除的短剧分类主键
     * @return 结果
     */
    @Override
    public int deleteDramaCategoryByIds(Long[] ids)
    {
        return dramaCategoryMapper.deleteDramaCategoryByIds(ids);
    }

    /**
     * 删除短剧分类信息
     * 
     * @param id 短剧分类主键
     * @return 结果
     */
    @Override
    public int deleteDramaCategoryById(Long id)
    {
        return dramaCategoryMapper.deleteDramaCategoryById(id);
    }

    @Override
    public List<DramaCategory> listInfo(DramaCategory dramaCategory) {
        return dramaCategoryMapper.selectDramaCategoryListInfo(dramaCategory);
    }

    @Override
    public Integer selectDramaCategoryByParentId(Long id) {
        return dramaCategoryMapper.selectDramaCategoryByParentId(id);
    }
}
