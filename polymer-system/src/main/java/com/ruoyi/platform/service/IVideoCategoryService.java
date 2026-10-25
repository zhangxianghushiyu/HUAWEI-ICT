package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.VideoCategory;

/**
 * 视频分类Service接口
 *
 * @author ruoyi
 */
public interface IVideoCategoryService {
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
     * 批量删除视频分类
     */
    int deleteVideoCategoryByIds(Long[] ids);

    /**
     * 删除视频分类信息
     */
    int deleteVideoCategoryById(Long id);
}
