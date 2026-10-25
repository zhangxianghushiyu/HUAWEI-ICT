package com.ruoyi.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.platform.domain.Ebook;
import com.ruoyi.platform.domain.MusicStore;
import com.ruoyi.platform.mapper.EbookMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.EbookItemMapper;
import com.ruoyi.platform.domain.EbookItem;
import com.ruoyi.platform.service.IEbookItemService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 电子书章节Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
@Service
public class EbookItemServiceImpl implements IEbookItemService 
{
    @Autowired
    private EbookItemMapper ebookItemMapper;
    @Autowired
    private EbookMapper ebookMapper;

    /**
     * 查询电子书章节
     * 
     * @param id 电子书章节主键
     * @return 电子书章节
     */
    @Override
    public EbookItem selectEbookItemById(Long id)
    {
        return ebookItemMapper.selectEbookItemById(id);
    }

    /**
     * 查询电子书章节列表
     * 
     * @param ebookItem 电子书章节
     * @return 电子书章节
     */
    @Override
    public List<EbookItem> selectEbookItemList(EbookItem ebookItem)
    {
        return ebookItemMapper.selectEbookItemList(ebookItem);
    }

    /**
     * 新增电子书章节
     * 
     * @param ebookItem 电子书章节
     * @return 结果
     */
    @Override
    @Transactional
    public int insertEbookItem(EbookItem ebookItem)
    {
        Date nowDate = DateUtils.getNowDate();
        ebookItem.setCreateTime(nowDate);
        ebookItem.setUpdateTime(nowDate);
        //修改主信息更新时间
        Ebook ebook = ebookMapper.selectEbookById(ebookItem.getEbookId());
        if (Objects.nonNull(ebook)){
            ebook.setUpdateTime(nowDate);
            ebookMapper.updateEbook(ebook);
        }
        return ebookItemMapper.insertEbookItem(ebookItem);
    }

    /**
     * 修改电子书章节
     * 
     * @param ebookItem 电子书章节
     * @return 结果
     */
    @Override
    @Transactional
    public int updateEbookItem(EbookItem ebookItem)
    {
        Date nowDate = DateUtils.getNowDate();
        ebookItem.setUpdateTime(nowDate);
        //修改主信息更新时间
        Ebook ebook = ebookMapper.selectEbookById(ebookItem.getEbookId());
        if (Objects.nonNull(ebook)){
            ebook.setUpdateTime(nowDate);
            ebookMapper.updateEbook(ebook);
        }
        return ebookItemMapper.updateEbookItem(ebookItem);
    }

    /**
     * 批量删除电子书章节
     * 
     * @param ids 需要删除的电子书章节主键
     * @return 结果
     */
    @Override
    public int deleteEbookItemByIds(Long[] ids)
    {
        return ebookItemMapper.deleteEbookItemByIds(ids);
    }

    /**
     * 删除电子书章节信息
     * 
     * @param id 电子书章节主键
     * @return 结果
     */
    @Override
    public int deleteEbookItemById(Long id)
    {
        return ebookItemMapper.deleteEbookItemById(id);
    }

    @Override
    @Transactional
    public String importDataHandle(List<EbookItem> dataHandles, Boolean isUpdateSupport, String operName,Long ebookId) {
        if (StringUtils.isNull(dataHandles) || dataHandles.isEmpty()) {
            throw new ServiceException("导入数据不能为空！");
        }
        if (dataHandles.size() >= 10000) {
            throw new ServiceException("模版数据导入上限！");
        }
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        List<EbookItem> list = new ArrayList<>();
        String now = DateUtil.now();
        for (EbookItem dataHandle : dataHandles) {
            EbookItem ebookItem = new EbookItem();
            BeanUtils.copyProperties(dataHandle, ebookItem);
            ebookItem.setEbookId(ebookId);
            list.add(ebookItem);
        }
        int result = ebookItemMapper.batchInsert(list, now);
        if (result <= 0) {
            failureMsg.insert(0, "很抱歉，导入失败！");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + dataHandles.size() + " 条，数据如下：");
        }
        //修改更新时间
        Ebook ebook = ebookMapper.selectEbookById(ebookId);
        if (Objects.nonNull(ebook)){
            ebook.setUpdateTime(new Date());
            ebookMapper.updateEbook(ebook);
        }
        return successMsg.toString();
    }

    @Override
    public int undercarriage(Long[] ids) {
        return ebookItemMapper.undercarriage(ids);
    }

    @Override
    public int grounding(Long[] ids) {
        return ebookItemMapper.grounding(ids);
    }
}
