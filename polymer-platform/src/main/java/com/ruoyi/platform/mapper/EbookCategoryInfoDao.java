package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.EbookCategoryInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EbookCategoryInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(EbookCategoryInfo record);

    int insertSelective(EbookCategoryInfo record);

    EbookCategoryInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EbookCategoryInfo record);

    int updateByPrimaryKey(EbookCategoryInfo record);

    /**
     * 查询电子书所属分类
     */
    List<String> selectEbookInfo(@Param("ebookId")Integer ebookId);
}