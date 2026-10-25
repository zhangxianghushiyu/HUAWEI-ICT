package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.VideoCategory;

/**
 * 视频分类Mapper接口
 *
 * @author ruoyi
 */
public interface VideoCategoryMapper {
    /**
     * 查询视频分类
     */
    VideoCategory selectVideoCategoryById(Long id);

    /**
     * 查询视频分类列表
     */
    List<VideoCategory> selectVideoCategoryList(VideoCategory videoCategory);

    /**
     * 新增视频分类
     */
    int insertVideoCategory(VideoCategory videoCategory);

    /**
     * 修改视频分类
     */
    int updateVideoCategory(VideoCategory videoCategory);

    /**
     * 删除视频分类
     */
    int deleteVideoCategoryById(Long id);

    /**
     * 批量删除视频分类
     */
    int deleteVideoCategoryByIds(Long[] ids);
}
