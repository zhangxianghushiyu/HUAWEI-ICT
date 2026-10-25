package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.Ebook;
import com.ruoyi.platform.vo.EbookListVO;
import com.ruoyi.platform.vo.EbookQueryListVO;
import com.ruoyi.platform.vo.EbookRecommendLikeListVO;
import com.ruoyi.platform.vo.EbookRecommendListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EbookDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Ebook record);

    int insertSelective(Ebook record);

    Ebook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ebook record);

    int updateByPrimaryKey(Ebook record);

    /**
     * 今日推荐
     */
    List<EbookRecommendListVO> selectEbookRecommendListVO();

    /**
     * 猜你喜欢
     */
    List<EbookRecommendLikeListVO> selectEbookRecommendLikeListVO();

    /**
     * 查询精品
     */
    List<EbookListVO> selectEbookListVOPremiumProducts();

    /**
     * 查询免费
     */
    List<EbookListVO> selectEbookListVOFree();

    /**
     * 多条件查询
     */
    List<EbookQueryListVO> selectEbookQueryListVONoCategoryId(@Param("isEnd") Integer isEnd,
                                                              @Param("wordsType") Integer wordsType, @Param("sortType") Integer sortType);

    /**
     * 多条件查询
     */
    List<EbookQueryListVO> selectEbookQueryListVOHaveCategoryId(@Param("categoryId") Integer categoryId, @Param("isEnd") Integer isEnd,
                                                                @Param("wordsType") Integer wordsType, @Param("sortType") Integer sortType);

    Ebook selectEbookById(int id);
}