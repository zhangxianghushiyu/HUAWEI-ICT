package com.ruoyi.platform.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.VideoCategoryMapper;
import com.ruoyi.platform.domain.VideoCategory;
import com.ruoyi.platform.service.IVideoCategoryService;

/**
 * 视频分类Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class VideoCategoryServiceImpl implements IVideoCategoryService {
    @Autowired
    private VideoCategoryMapper videoCategoryMapper;

    @Override
    public VideoCategory selectVideoCategoryById(Long id) {
        return videoCategoryMapper.selectVideoCategoryById(id);
    }

    @Override
    public List<VideoCategory> selectVideoCategoryList(VideoCategory videoCategory) {
        return videoCategoryMapper.selectVideoCategoryList(videoCategory);
    }

    @Override
    public int insertVideoCategory(VideoCategory videoCategory) {
        return videoCategoryMapper.insertVideoCategory(videoCategory);
    }

    @Override
    public int updateVideoCategory(VideoCategory videoCategory) {
        return videoCategoryMapper.updateVideoCategory(videoCategory);
    }

    @Override
    public int deleteVideoCategoryByIds(Long[] ids) {
        return videoCategoryMapper.deleteVideoCategoryByIds(ids);
    }

    @Override
    public int deleteVideoCategoryById(Long id) {
        return videoCategoryMapper.deleteVideoCategoryById(id);
    }
}
