package com.ruoyi.platform.service;

import java.util.List;

import com.ruoyi.platform.domain.DramaCategory;

/**
 * 短剧分类Service接口
 *
 * @author ruoyi
 * @date 2024-11-10
 */
public interface IDramaCategoryService {
    /**
     * 查询短剧分类
     *
     * @param id 短剧分类主键
     * @return 短剧分类
     */
    public DramaCategory selectDramaCategoryById(Long id);

    /**
     * 查询短剧分类列表
     *
     * @param dramaCategory 短剧分类
     * @return 短剧分类集合
     */
    public List<DramaCategory> selectDramaCategoryList(DramaCategory dramaCategory);

    /**
     * 新增短剧分类
     *
     * @param dramaCategory 短剧分类
     * @return 结果
     */
    public int insertDramaCategory(DramaCategory dramaCategory);

    /**
     * 修改短剧分类
     *
     * @param dramaCategory 短剧分类
     * @return 结果
     */
    public int updateDramaCategory(DramaCategory dramaCategory);

    /**
     * 批量删除短剧分类
     *
     * @param ids 需要删除的短剧分类主键集合
     * @return 结果
     */
    public int deleteDramaCategoryByIds(Long[] ids);

    /**
     * 删除短剧分类信息
     *
     * @param id 短剧分类主键
     * @return 结果
     */
    public int deleteDramaCategoryById(Long id);

    /**
     * 查询分类
     *
     * @return
     */
    List<DramaCategory> listInfo(DramaCategory dramaCategory);

    Integer selectDramaCategoryByParentId(Long id);
}
