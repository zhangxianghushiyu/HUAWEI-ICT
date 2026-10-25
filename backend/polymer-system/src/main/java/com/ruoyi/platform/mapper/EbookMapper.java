package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.Ebook;

/**
 * 电子书主信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
public interface EbookMapper 
{
    /**
     * 查询电子书主信息
     * 
     * @param id 电子书主信息主键
     * @return 电子书主信息
     */
    public Ebook selectEbookById(Long id);

    /**
     * 查询电子书主信息列表
     * 
     * @param ebook 电子书主信息
     * @return 电子书主信息集合
     */
    public List<Ebook> selectEbookList(Ebook ebook);

    /**
     * 新增电子书主信息
     * 
     * @param ebook 电子书主信息
     * @return 结果
     */
    public int insertEbook(Ebook ebook);

    /**
     * 修改电子书主信息
     * 
     * @param ebook 电子书主信息
     * @return 结果
     */
    public int updateEbook(Ebook ebook);

    /**
     * 删除电子书主信息
     * 
     * @param id 电子书主信息主键
     * @return 结果
     */
    public int deleteEbookById(Long id);

    /**
     * 批量删除电子书主信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEbookByIds(Long[] ids);

    /**
     *
     * @return 结果
     */
    public int undercarriage(Long[] ids);

    /**
     *
     * @return 结果
     */
    public int grounding(Long[] ids);

}
