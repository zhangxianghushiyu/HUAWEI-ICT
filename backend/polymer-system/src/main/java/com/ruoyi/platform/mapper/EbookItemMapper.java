package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.EbookItem;
import com.ruoyi.platform.domain.MusicStore;
import org.apache.ibatis.annotations.Param;

/**
 * 电子书章节Mapper接口
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
public interface EbookItemMapper 
{
    /**
     * 查询电子书章节
     * 
     * @param id 电子书章节主键
     * @return 电子书章节
     */
    public EbookItem selectEbookItemById(Long id);

    /**
     * 查询电子书章节列表
     * 
     * @param ebookItem 电子书章节
     * @return 电子书章节集合
     */
    public List<EbookItem> selectEbookItemList(EbookItem ebookItem);

    /**
     * 新增电子书章节
     * 
     * @param ebookItem 电子书章节
     * @return 结果
     */
    public int insertEbookItem(EbookItem ebookItem);

    /**
     * 修改电子书章节
     * 
     * @param ebookItem 电子书章节
     * @return 结果
     */
    public int updateEbookItem(EbookItem ebookItem);

    /**
     * 删除电子书章节
     * 
     * @param id 电子书章节主键
     * @return 结果
     */
    public int deleteEbookItemById(Long id);

    /**
     * 批量删除电子书章节
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEbookItemByIds(Long[] ids);


    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<EbookItem> list, @Param("createTime") String createTime);

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
