package com.ruoyi.platform.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.EbookMapper;
import com.ruoyi.platform.domain.Ebook;
import com.ruoyi.platform.service.IEbookService;

/**
 * 电子书主信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-05-21
 */
@Service
public class EbookServiceImpl implements IEbookService 
{
    @Autowired
    private EbookMapper ebookMapper;

    /**
     * 查询电子书主信息
     * 
     * @param id 电子书主信息主键
     * @return 电子书主信息
     */
    @Override
    public Ebook selectEbookById(Long id)
    {
        return ebookMapper.selectEbookById(id);
    }

    /**
     * 查询电子书主信息列表
     * 
     * @param ebook 电子书主信息
     * @return 电子书主信息
     */
    @Override
    public List<Ebook> selectEbookList(Ebook ebook)
    {
        return ebookMapper.selectEbookList(ebook);
    }

    /**
     * 新增电子书主信息
     * 
     * @param ebook 电子书主信息
     * @return 结果
     */
    @Override
    public int insertEbook(Ebook ebook)
    {
        Date nowDate = DateUtils.getNowDate();
        ebook.setCreateTime(nowDate);
        ebook.setUpdateTime(nowDate);
        return ebookMapper.insertEbook(ebook);
    }

    /**
     * 修改电子书主信息
     * 
     * @param ebook 电子书主信息
     * @return 结果
     */
    @Override
    public int updateEbook(Ebook ebook)
    {
        ebook.setUpdateTime(DateUtils.getNowDate());
        return ebookMapper.updateEbook(ebook);
    }

    /**
     * 批量删除电子书主信息
     * 
     * @param ids 需要删除的电子书主信息主键
     * @return 结果
     */
    @Override
    public int deleteEbookByIds(Long[] ids)
    {
        return ebookMapper.deleteEbookByIds(ids);
    }

    /**
     * 删除电子书主信息信息
     * 
     * @param id 电子书主信息主键
     * @return 结果
     */
    @Override
    public int deleteEbookById(Long id)
    {
        return ebookMapper.deleteEbookById(id);
    }

    @Override
    public int undercarriage(Long[] ids) {
        return ebookMapper.undercarriage(ids);
    }

    @Override
    public int grounding(Long[] ids) {
        return ebookMapper.grounding(ids);
    }
}
