package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.EbookRecord;
import com.ruoyi.platform.vo.EbookRecordVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EbookRecordDao{
    @Delete("Delete from t_ebook_record  WHERE user_id = #{userId} AND ebook_id = #{eBookId}")
    void clearLatestFlag(@Param("userId") int userId, @Param("eBookId") int eBookId);

    int insert(EbookRecord record);

    List<EbookRecordVo> selectRecentRecords(@Param("userId") int userId);
}