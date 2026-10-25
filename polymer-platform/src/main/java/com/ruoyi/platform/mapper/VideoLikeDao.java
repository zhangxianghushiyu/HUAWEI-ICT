package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.VideoLike;
import com.ruoyi.platform.vo.MyVideoLikeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoLikeDao {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(VideoLike record);

    int insert(VideoLike record);

    VideoLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VideoLike record);

    int updateByPrimaryKey(VideoLike record);

    /**
     * 查看用户是否点赞过该视频
     */

    VideoLike selectVideoLikeByUserIdAndVideoId(@Param("userId")Integer userId,
                                                @Param("videoId")Integer videoId);

    List<MyVideoLikeVO> selectMyVideoLikeVO(@Param("userId") Integer userId);

    /**
     * 查询该视频的点赞量
     */
    Integer selectLikeCount(@Param("videoId")Integer videoId);
}
