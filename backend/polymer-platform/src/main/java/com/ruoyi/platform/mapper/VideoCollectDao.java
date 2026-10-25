package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.VideoCollect;
import com.ruoyi.platform.vo.MyVideoCollectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoCollectDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(VideoCollect record);

    int insert(VideoCollect record);

    VideoCollect selectByPrimaryKey(Integer id);

//    int updateByPrimaryKeySelective(VideoCollect record);

    int updateByPrimaryKey(VideoCollect record);

    /**
     * 查看用户是否收藏过该视频
     */
    VideoCollect selectVideoCollectionByUserIdAndVideoId(@Param("userId") Integer userId,
                                                         @Param("videoId") Integer videoId);

    List<MyVideoCollectVO> selectMyVideoCollectVO(@Param("userId") Integer userId);
}
