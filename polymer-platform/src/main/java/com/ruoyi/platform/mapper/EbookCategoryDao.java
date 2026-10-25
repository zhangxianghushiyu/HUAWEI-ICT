package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.EbookCategory;
import com.ruoyi.platform.vo.EbookCategoryListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EbookCategoryDao {
    int deleteByPrimaryKey(Integer id);

    int insert(EbookCategory record);

    int insertSelective(EbookCategory record);

    EbookCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EbookCategory record);

    int updateByPrimaryKey(EbookCategory record);

    /**
     * 分类
     */
    List<EbookCategoryListVO> selectEbookCategoryListVO();
}