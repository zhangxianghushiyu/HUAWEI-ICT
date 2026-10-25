package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.DramaPublicize;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DramaPublicizeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DramaPublicize record);

    int insertSelective(DramaPublicize record);

    DramaPublicize selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DramaPublicize record);

    int updateByPrimaryKey(DramaPublicize record);

    /**
     * 通过用户ID及单集ID查询信息
     */
    DramaPublicize selectDramaPublicizeByUserIdAndDramaDetailsId(@Param("userId") Integer userId,
                                                                 @Param("dramaDetailsId") Integer dramaDetailsId);
}