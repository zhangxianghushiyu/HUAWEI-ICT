package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.DramaWatch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DramaWatchDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DramaWatch record);

    int insertSelective(DramaWatch record);

    DramaWatch selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DramaWatch record);

    int updateByPrimaryKey(DramaWatch record);


    /**
     * 查询观看记录
     * @param userId
     * @param dramaId
     * @return
     */
    DramaWatch selectDramaWatchByUserIdAndDramaId(@Param("userId")Integer userId,
                                                @Param("dramaId")Integer dramaId);



    /**
     * 查询观看记录的具体集数
     * @param userId
     * @param dramaId
     * @return
     */
    Integer selectWatchGatherByUserIdAndDramaId(@Param("userId")Integer userId,
                                                  @Param("dramaId")Integer dramaId);
}