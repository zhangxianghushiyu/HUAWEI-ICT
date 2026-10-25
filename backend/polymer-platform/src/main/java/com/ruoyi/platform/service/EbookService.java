package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.Ebook;
import com.ruoyi.platform.vo.*;

import java.util.List;

public interface EbookService {

    /**
     * 查询电子书主信息
     *
     * @param id 电子书主信息主键
     * @return 电子书主信息
     */
     Ebook selectEbookById(int id);

    /**
     * 今日推荐
     */
    List<EbookRecommendListVO> getEbookRecommendListVO();

    /**
     * 猜你喜欢
     */
    List<EbookRecommendLikeListVO> getListEbookRecommendLikeListVO();

    /**
     * 分类
     */
    List<EbookCategoryListVO> getEbookCategoryListVO();

    /**
     * 精品免费
     */
    List<EbookListVO> getEbookListVO(Integer type);

    /**
     * 多条件查询
     */
    List<EbookQueryListVO> getEbookQueryListVO(Integer categoryId,Integer isEnd,Integer wordsType,Integer sortType);

    /**
     * 查询列表
     */
    List<EbookItemListVO> getEbookItemListVO(Integer ebookId);

    /**
     * 保存小说游览记录
     */
    String saveReadingRecord(int userId, int eBookId, int chapterId, int progress);


    /**
     * 删除浏览记录
     */
    String clearLatestFlag(int userId, int eBookId);

    /**
     * 删除浏览记录
     */
    List<EbookRecordVo>  getEbookRecordVo(int userId);
}
