package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.EbookItem;
import com.ruoyi.platform.vo.EbookItemListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EbookItemDao {
    int deleteByPrimaryKey(Integer id);

    int insert(EbookItem record);

    int insertSelective(EbookItem record);

    EbookItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EbookItem record);

    int updateByPrimaryKey(EbookItem record);

    /**
     * 查询列表
     */
    List<EbookItemListVO> selectEbookItemListVO(@Param("ebookId")Integer ebookId);
}