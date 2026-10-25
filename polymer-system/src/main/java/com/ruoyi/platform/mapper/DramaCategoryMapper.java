package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.DramaCategory;

/**
 * 短剧分类Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-10
 */
public interface DramaCategoryMapper 
{
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
     * 删除短剧分类
     * 
     * @param id 短剧分类主键
     * @return 结果
     */
    public int deleteDramaCategoryById(Long id);

    /**
     * 批量删除短剧分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDramaCategoryByIds(Long[] ids);


    /**
     * 查询短剧分类列表
     *
     * @param dramaCategory 短剧分类
     * @return 短剧分类集合
     */
    public List<DramaCategory> selectDramaCategoryListInfo(DramaCategory dramaCategory);

    Integer selectDramaCategoryByParentId(Long id);
}
