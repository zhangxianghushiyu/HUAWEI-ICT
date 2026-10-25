package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.VideoPublicize;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VideoPublicizeDao {

    int insertSelective(VideoPublicize record);

    /**
     * 通过用户ID及视频ID查询解锁记录
     */
    VideoPublicize selectVideoPublicizeByUserIdAndVideoId(@Param("userId") Integer userId,
                                                          @Param("videoId") Integer videoId);
}
