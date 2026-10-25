package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.DramaLook;
import com.ruoyi.platform.vo.MyDramaLookVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DramaLookDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DramaLook record);

    int insertSelective(DramaLook record);

    DramaLook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DramaLook record);

    int updateByPrimaryKey(DramaLook record);

    /**
     * 查询观看记录
     *
     * @param userId
     * @param dramaId
     * @return
     */
    DramaLook selectDramaLookByUserIdAndDramaId(@Param("userId") Integer userId,
                                                @Param("dramaId") Integer dramaId);


    /**
     * 查询我的浏览记录
     */
    List<MyDramaLookVO> selectDramaLookRecordListVO(@Param("userId") Integer userId);

    /**
     * 删除短剧浏览记录
     */
    Integer removeInfoByDramaId(@Param("id") Integer id);
}