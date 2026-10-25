package com.ruoyi.platform.service.impl;

import com.ruoyi.platform.domain.Ebook;
import com.ruoyi.platform.domain.EbookItem;
import com.ruoyi.platform.domain.EbookRecord;
import com.ruoyi.platform.mapper.*;
import com.ruoyi.platform.service.EbookService;
import com.ruoyi.platform.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class EbookServiceImpl implements EbookService {

    private static final Logger log = LoggerFactory.getLogger(EbookServiceImpl.class);
    @Autowired
    private EbookDao ebookDao;
    @Autowired
    private EbookCategoryDao ebookCategoryDao;
    @Autowired
    private EbookCategoryInfoDao ebookCategoryInfoDao;
    @Autowired
    private EbookItemDao ebookItemDao;
    @Autowired
    private EbookRecordDao ebookRecordDao;

    @Override
    public Ebook selectEbookById(int id) {
        return ebookDao.selectEbookById(id);
    }

    @Override
    public List<EbookRecommendListVO> getEbookRecommendListVO() {
        return ebookDao.selectEbookRecommendListVO();
    }

    @Override
    public List<EbookRecommendLikeListVO> getListEbookRecommendLikeListVO() {
        List<EbookRecommendLikeListVO> ebookRecommendLikeListVOS = ebookDao.selectEbookRecommendLikeListVO();
        if (!ebookRecommendLikeListVOS.isEmpty()) {
            //查询分类
            for (EbookRecommendLikeListVO ebookRecommendLikeListVO : ebookRecommendLikeListVOS) {
                ebookRecommendLikeListVO.setCategoryStr(ebookCategoryInfoDao.selectEbookInfo(ebookRecommendLikeListVO.getId()));
            }
        }
        return ebookRecommendLikeListVOS;
    }

    @Override
    public List<EbookCategoryListVO> getEbookCategoryListVO() {
        return ebookCategoryDao.selectEbookCategoryListVO();
    }

    @Override
    public List<EbookListVO> getEbookListVO(Integer type) {
        List<EbookListVO> ebookListVOS;
        if (type == 1) {
            //精品
            ebookListVOS = ebookDao.selectEbookListVOPremiumProducts();
        } else {
            //免费
            ebookListVOS = ebookDao.selectEbookListVOFree();
        }
        if (!ebookListVOS.isEmpty()) {
            for (EbookListVO ebookListVO : ebookListVOS) {
                ebookListVO.setCategoryStr(ebookCategoryInfoDao.selectEbookInfo(ebookListVO.getId()));
            }
        }
        return ebookListVOS;
    }

    @Override
    public List<EbookQueryListVO> getEbookQueryListVO(Integer categoryId, Integer isEnd, Integer wordsType, Integer sortType) {
        List<EbookQueryListVO> ebookQueryListVOS;
        if (Objects.isNull(categoryId)) {
            ebookQueryListVOS = ebookDao.selectEbookQueryListVONoCategoryId(isEnd, wordsType, sortType);
        } else {
            //查询该分类下的小说
            ebookQueryListVOS = ebookDao.selectEbookQueryListVOHaveCategoryId(categoryId, isEnd, wordsType, sortType);
        }
        if (!ebookQueryListVOS.isEmpty()) {
            for (EbookQueryListVO ebookListVO : ebookQueryListVOS) {
                ebookListVO.setCategoryStr(ebookCategoryInfoDao.selectEbookInfo(ebookListVO.getId()));
            }
        }
        return ebookQueryListVOS;
    }

    @Override
    public List<EbookItemListVO> getEbookItemListVO(Integer ebookId) {
        return ebookItemDao.selectEbookItemListVO(ebookId);
    }

    @Transactional
    @Override
    public String saveReadingRecord(int userId, int eBookId, int chapterId, int progress) {
        // 验证小说和章节是否存在
        Ebook ebook = selectEbookById(eBookId);
        if (ebook == null) {
            return "小说不存在";
        }

        EbookItem ebookItem = ebookItemDao.selectByPrimaryKey(chapterId);
        if (ebookItem == null || !ebookItem.getEbookId().equals(eBookId)) {
            return "章节不存在或不属于该小说";
        }

        // 清除之前的latest标记
        ebookRecordDao.clearLatestFlag(userId, eBookId);

        // 创建新的阅读记录
        EbookRecord record = new EbookRecord();
        record.setUserId(userId);
        record.setEBookId(eBookId);
        record.setChapterId(chapterId);
        record.setReadProgress(Math.min(100, Math.max(0, progress)));
        record.setReadTime(new Date());
        record.setIsLatest(1);

        ebookRecordDao.insert(record);
        return "保存成功";
    }

    @Override
    public String clearLatestFlag(int userId, int eBookId) {
        try{
            log.info("删除浏览记录userId{},ebookId{}",userId,eBookId);
            ebookRecordDao.clearLatestFlag(userId, eBookId);
        }catch (Exception e){
            log.info("删除异常{}",e.getMessage());
            return "删除失败";
        }
        return "删除成功";
    }

    @Override
    public List<EbookRecordVo> getEbookRecordVo(int userId) {
        List<EbookRecordVo> ebookRecordVos = ebookRecordDao.selectRecentRecords(userId);
        return ebookRecordVos;
    }


}
