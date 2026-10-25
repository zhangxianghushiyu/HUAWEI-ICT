package com.ruoyi.platform.mapper;

import java.util.List;
import com.ruoyi.platform.domain.Video;
import com.ruoyi.platform.dto.VideoListDTO;
import com.ruoyi.platform.vo.VideoListVO;
import org.apache.ibatis.annotations.Param;

/**
 * 视频Mapper接口
 * 
 * @author ruoyi
 * @date 2024-11-11
 */
public interface VideoMapper 
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
     * 删除视频
     * 
     * @param id 视频主键
     * @return 结果
     */
    public int deleteVideoById(Long id);

    /**
     * 批量删除视频
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVideoByIds(Long[] ids);

    /**
     * 批量下架视频
     *
     * @param ids 批量下架视频
     * @return 结果
     */
    public int undercarriage(Long[] ids);
    /**
     * 批量上架视频
     *
     * @param ids 批量下架视频
     * @return 结果
     */
    public int grounding(Long[] ids);

    /**
     * 按分类逻辑删除视频
     */
    public int delVideoByClassify(@Param("classifyId") Integer classifyId);

    /**
     * 按系列逻辑删除视频
     */
    public int delVideoBySeries(@Param("seriesName") String seriesName);

    /**
     * 按分类上架视频
     */
    public int groundingByClassify(@Param("classifyId") Integer classifyId);

    /**
     * 按系列上架视频
     */
    public int groundingBySeries(@Param("seriesName") String seriesName);

    /**
     * 按分类下架视频
     */
    public int undercarriageByClassify(@Param("classifyId") Integer classifyId);

    /**
     * 按系列下架视频
     */
    public int undercarriageBySeries(@Param("seriesName") String seriesName);

    /**
     * 查询系列名去重列表（可按分类过滤）
     */
    public List<String> selectSeriesNameList(@Param("classifyId") Integer classifyId);


    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<Video> list, @Param("createTime") String createTime);

    /**
     * 查询尚未解析视频宽高的记录（id + videoUrl）
     */
    List<Video> selectVideoSizeNull();

    /**
     * 更新视频宽高（videoUrl 为 null 时仅更新宽高，不更新地址）
     */
    int updateVideoSize(@Param("id") Long id,
                        @Param("videoWidth") Integer videoWidth,
                        @Param("videoHeight") Integer videoHeight,
                        @Param("videoUrl") String videoUrl);

    /**
     * 更新视频系列名与集数
     */
    int updateVideoSeriesName(@Param("id") Long id,
                              @Param("seriesName") String seriesName,
                              @Param("episodeNum") Integer episodeNum);
}
