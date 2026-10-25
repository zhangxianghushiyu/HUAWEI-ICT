package com.ruoyi.platform.service;

import java.util.List;
import com.ruoyi.platform.domain.Video;
import com.ruoyi.platform.dto.VideoListDTO;
import com.ruoyi.platform.vo.VideoListVO;

/**
 * 视频Service接口
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
public interface IVideoService 
{
    /**
     * 查询视频
     * 
     * @param id 视频主键
     * @return 视频
     */
    public Video selectVideoById(Long id);

    /**
     * 查询视频列表
     * 
     * @param video 视频
     * @return 视频集合
     */
    public List<Video> selectVideoList(Video video);

    /**
     * 查询视频列表
     *
     * @param video 视频
     * @return 视频集合
     */
    public List<VideoListVO> selectVideoListVO(VideoListDTO video);

    /**
     * 新增视频
     * 
     * @param video 视频
     * @return 结果
     */
    public int insertVideo(Video video);

    /**
     * 修改视频
     * 
     * @param video 视频
     * @return 结果
     */
    public int updateVideo(Video video);

    /**
     * 批量删除视频
     * 
     * @param ids 需要删除的视频主键集合
     * @return 结果
     */
    public int deleteVideoByIds(Long[] ids);

    /**
     * 批量下架视频
     *
     * @param ids 需要删除的视频主键集合
     * @return 结果
     */
    public int undercarriage(Long[] ids);

    /**
     * 批量上架视频
     *
     * @param ids 需要删除的视频主键集合
     * @return 结果
     */
    public int grounding(Long[] ids);

    /**
     * 按分类逻辑删除视频
     */
    public int delVideoByClassify(Integer classifyId);

    /**
     * 按系列逻辑删除视频
     */
    public int delVideoBySeries(String seriesName);

    /**
     * 按分类上架视频
     */
    public int groundingByClassify(Integer classifyId);

    /**
     * 按系列上架视频
     */
    public int groundingBySeries(String seriesName);

    /**
     * 按分类下架视频
     */
    public int undercarriageByClassify(Integer classifyId);

    /**
     * 按系列下架视频
     */
    public int undercarriageBySeries(String seriesName);

    /**
     * 查询系列名去重列表（可按分类过滤）
     */
    public List<String> selectSeriesNameList(Integer classifyId);

    /**
     * 批量上传视频（按分类与系列名）
     */
    public int batchAddVideos(Integer classifyId, String seriesName, List<Video> videos);

    /**
     * 删除视频信息
     * 
     * @param id 视频主键
     * @return 结果
     */
    public int deleteVideoById(Long id);

    /**
     * 导入数据
     *
     * @param dataHandles        数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importDataHandle(List<VideoListDTO> dataHandles, Boolean isUpdateSupport, String operName);

    /**
     * 异步解析并转码所有未处理视频为 9:16
     */
    public void refreshVideoSize();

    /**
     * 异步回填所有视频的系列名与集数
     */
    public void refreshVideoSeries();
}
