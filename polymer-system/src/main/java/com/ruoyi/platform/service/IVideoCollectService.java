package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.VideoCollect;
import com.ruoyi.platform.dto.VideoCollectListDTO;
import com.ruoyi.platform.vo.VideoCollectListVO;

/**
 * 用户收藏短视频Service接口
 *
 * @author ruoyi
 * @date 2024-11-11
 */
public interface IVideoCollectService
{
    /**
     * 查询用户收藏短视频
     *
     * @param id 用户收藏短视频主键
     * @return 用户收藏短视频
     */
    public VideoCollect selectVideoCollectById(Long id);

    /**
     * 查询用户收藏短视频列表
     *
     * @param videoCollect 用户收藏短视频
     * @return 用户收藏短视频集合
     */
    public List<VideoCollect> selectVideoCollectList(VideoCollect videoCollect);

    /**
     * 新增用户收藏短视频
     *
     * @param videoCollect 用户收藏短视频
     * @return 结果
     */
    public int insertVideoCollect(VideoCollect videoCollect);

    /**
     * 修改用户收藏短视频
     *
     * @param videoCollect 用户收藏短视频
     * @return 结果
     */
    public int updateVideoCollect(VideoCollect videoCollect);

    /**
     * 批量删除用户收藏短视频
     *
     * @param ids 需要删除的用户收藏短视频主键集合
     * @return 结果
     */
    public int deleteVideoCollectByIds(Long[] ids);

    /**
     * 删除用户收藏短视频信息
     *
     * @param id 用户收藏短视频主键
     * @return 结果
     */
    public int deleteVideoCollectById(Long id);

    /**
     * 查询收藏短视频列表信息
     *
     */
    List<VideoCollectListVO> selectVideoCollectListVO(VideoCollectListDTO collectListDTO);

}
