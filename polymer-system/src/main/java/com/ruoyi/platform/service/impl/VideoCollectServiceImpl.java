package com.ruoyi.platform.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.dto.VideoCollectListDTO;
import com.ruoyi.platform.vo.VideoCollectListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.VideoCollectMapper;
import com.ruoyi.platform.domain.VideoCollect;
import com.ruoyi.platform.service.IVideoCollectService;

/**
 * 用户收藏短视频Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-11
 */
@Service
public class VideoCollectServiceImpl implements IVideoCollectService
{
    @Autowired
    private VideoCollectMapper videoCollectMapper;

    /**
     * 查询用户收藏短视频
     *
     * @param id 用户收藏短视频主键
     * @return 用户收藏短视频
     */
    @Override
    public VideoCollect selectVideoCollectById(Long id)
    {
        return videoCollectMapper.selectVideoCollectById(id);
    }

    /**
     * 查询用户收藏短视频列表
     *
     * @param videoCollect 用户收藏短视频
     * @return 用户收藏短视频
     */
    @Override
    public List<VideoCollect> selectVideoCollectList(VideoCollect videoCollect)
    {
        return videoCollectMapper.selectVideoCollectList(videoCollect);
    }

    /**
     * 新增用户收藏短视频
     *
     * @param videoCollect 用户收藏短视频
     * @return 结果
     */
    @Override
    public int insertVideoCollect(VideoCollect videoCollect)
    {
        videoCollect.setCreateTime(DateUtils.getNowDate());
        return videoCollectMapper.insertVideoCollect(videoCollect);
    }

    /**
     * 修改用户收藏短视频
     *
     * @param videoCollect 用户收藏短视频
     * @return 结果
     */
    @Override
    public int updateVideoCollect(VideoCollect videoCollect)
    {
        videoCollect.setUpdateTime(DateUtils.getNowDate());
        return videoCollectMapper.updateVideoCollect(videoCollect);
    }

    /**
     * 批量删除用户收藏短视频
     *
     * @param ids 需要删除的用户收藏短视频主键
     * @return 结果
     */
    @Override
    public int deleteVideoCollectByIds(Long[] ids)
    {
        return videoCollectMapper.deleteVideoCollectByIds(ids);
    }

    /**
     * 删除用户收藏短视频信息
     *
     * @param id 用户收藏短视频主键
     * @return 结果
     */
    @Override
    public int deleteVideoCollectById(Long id)
    {
        return videoCollectMapper.deleteVideoCollectById(id);
    }

    /**
     * 查询收藏短视频列表信息
     * @param collectListDTO
     * @return 结果
     */
    @Override
    public List<VideoCollectListVO> selectVideoCollectListVO(VideoCollectListDTO collectListDTO){
        return videoCollectMapper.selectVideoCollectListVO(collectListDTO);
    }
}
