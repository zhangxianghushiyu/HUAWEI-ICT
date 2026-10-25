package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.EbookItem;
import com.ruoyi.platform.domain.MusicStore;

/**
 * 电子书章节Service接口
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
public interface IEbookItemService 
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
     * 批量删除电子书章节
     * 
     * @param ids 需要删除的电子书章节主键集合
     * @return 结果
     */
    public int deleteEbookItemByIds(Long[] ids);

    /**
     * 删除电子书章节信息
     * 
     * @param id 电子书章节主键
     * @return 结果
     */
    public int deleteEbookItemById(Long id);

    /**
     * 导入数据
     *
     * @param dataHandles        数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importDataHandle(List<EbookItem> dataHandles, Boolean isUpdateSupport,
                                   String operName,Long ebookId);

    /**
     *
     * @param ids 需要删除的视频主键集合
     * @return 结果
     */
    public int undercarriage(Long[] ids);

    /**
     *
     * @param ids 需要删除的视频主键集合
     * @return 结果
     */
    public int grounding(Long[] ids);
}
