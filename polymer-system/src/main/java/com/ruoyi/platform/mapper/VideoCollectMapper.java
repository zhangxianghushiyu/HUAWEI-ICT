package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.VideoCollect;
import com.ruoyi.platform.dto.VideoCollectListDTO;
import com.ruoyi.platform.vo.VideoCollectListVO;

/**
 * 用户收藏短视频Mapper接口
 *
 * @author ruoyi
 * @date 2024-11-11
 */
public interface VideoCollectMapper
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
     * 删除用户收藏短视频
     *
     * @param id 用户收藏短视频主键
     * @return 结果
     */
    public int deleteVideoCollectById(Long id);

    /**
     * 批量删除用户收藏短视频
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVideoCollectByIds(Long[] ids);

    /**
     * 查询短视频收藏列表
     * @param collectListDTO 短视频收藏
     * @return 短视频收藏集合
     */
    List<VideoCollectListVO> selectVideoCollectListVO(VideoCollectListDTO collectListDTO);
}
