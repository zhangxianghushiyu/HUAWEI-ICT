package com.ruoyi.platform.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.platform.dto.VideoListDTO;
import com.ruoyi.platform.vo.VideoListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.VideoMapper;
import com.ruoyi.platform.domain.Video;
import com.ruoyi.platform.service.AliService;
import com.ruoyi.platform.service.IVideoService;
import com.ruoyi.platform.utils.VideoUtil;
import org.springframework.transaction.annotation.Transactional;

/**
 * 视频Service业务层处理
 *
 * @author ruoyi
 * @date 2024-11-11
 */
@Service
@Slf4j
public class VideoServiceImpl implements IVideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private AliService aliService;
    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 查询视频
     *
     * @param id 视频主键
     * @return 视频
     */
    @Override
    public Video selectVideoById(Long id) {
        return videoMapper.selectVideoById(id);
    }

    /**
     * 查询视频列表
     *
     * @param video 视频
     * @return 视频
     */
    @Override
    public List<Video> selectVideoList(Video video) {
        return videoMapper.selectVideoList(video);
    }

    @Override
    public List<VideoListVO> selectVideoListVO(VideoListDTO video) {
        return videoMapper.selectVideoListVO(video);
    }

    /**
     * 新增视频
     *
     * @param video 视频
     * @return 结果
     */
    @Override
    public int insertVideo(Video video) {
        video.setCreateTime(DateUtils.getNowDate());
        fillSeriesInfo(video);
        int rows = videoMapper.insertVideo(video);
        asyncTranscodeVideo(video);
        return rows;
    }

    /**
     * 修改视频
     *
     * @param video 视频
     * @return 结果
     */
    @Override
    public int updateVideo(Video video) {
        video.setUpdateTime(DateUtils.getNowDate());
        fillSeriesInfo(video);
        int rows = videoMapper.updateVideo(video);
        asyncTranscodeVideo(video);
        return rows;
    }

    /**
     * 批量删除视频
     *
     * @param ids 需要删除的视频主键
     * @return 结果
     */
    @Override
    public int deleteVideoByIds(Long[] ids) {
        return videoMapper.deleteVideoByIds(ids);
    }

    @Override
    public int undercarriage(Long[] ids) {
        return videoMapper.undercarriage(ids);
    }

    @Override
    public int grounding(Long[] ids) {
        return videoMapper.grounding(ids);
    }

    @Override
    public int delVideoByClassify(Integer classifyId) {
        return videoMapper.delVideoByClassify(classifyId);
    }

    @Override
    public int delVideoBySeries(String seriesName) {
        return videoMapper.delVideoBySeries(seriesName);
    }

    @Override
    public int groundingByClassify(Integer classifyId) {
        return videoMapper.groundingByClassify(classifyId);
    }

    @Override
    public int groundingBySeries(String seriesName) {
        return videoMapper.groundingBySeries(seriesName);
    }

    @Override
    public int undercarriageByClassify(Integer classifyId) {
        return videoMapper.undercarriageByClassify(classifyId);
    }

    @Override
    public int undercarriageBySeries(String seriesName) {
        return videoMapper.undercarriageBySeries(seriesName);
    }

    @Override
    public List<String> selectSeriesNameList(Integer classifyId) {
        return videoMapper.selectSeriesNameList(classifyId);
    }

    @Override
    @Transactional
    public int batchAddVideos(Integer classifyId, String seriesName, List<Video> videos) {
        if (StringUtils.isEmpty(seriesName)) {
            throw new ServiceException("请选择系列");
        }
        if (videos == null || videos.isEmpty()) {
            throw new ServiceException("请选择要上传的视频");
        }
        int rows = 0;
        for (Video video : videos) {
            if (StringUtils.isEmpty(video.getVideoUrl())) {
                throw new ServiceException("存在未上传成功的视频，请重试");
            }
            video.setId(null);
            video.setClassifyId(classifyId);
            video.setSeriesName(seriesName);
            video.setStatus(1);
            video.setDelStatus(1);
            video.setCreateTime(DateUtils.getNowDate());
            if (StringUtils.isEmpty(video.getContent())) {
                video.setContent(video.getName());
            }
            fillEpisodeNum(video);
            videoMapper.insertVideo(video);
            asyncTranscodeVideo(video);
            rows++;
        }
        return rows;
    }

    /**
     * 从名称末尾数字解析集数
     */
    private void fillEpisodeNum(Video video) {
        String name = video.getName();
        if (StringUtils.isEmpty(name)) {
            return;
        }
        Matcher matcher = Pattern.compile("(\\d+)$").matcher(name);
        if (matcher.find()) {
            video.setEpisodeNum(Integer.valueOf(matcher.group(1)));
        }
    }

    /**
     * 删除视频信息
     *
     * @param id 视频主键
     * @return 结果
     */
    @Override
    public int deleteVideoById(Long id) {
        return videoMapper.deleteVideoById(id);
    }

    @Transactional
    @Override
    public String importDataHandle(List<VideoListDTO> dataHandles, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(dataHandles) || dataHandles.isEmpty()) {
            throw new ServiceException("导入数据不能为空！");
        }
        if (dataHandles.size() >= 1000) {
            throw new ServiceException("模版数据导入上限！");
        }
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        List<Video> list = new ArrayList<>();
        String now = DateUtil.now();
        for (VideoListDTO dataHandle : dataHandles) {
            Video video = new Video();
            BeanUtils.copyProperties(dataHandle, video);
            fillSeriesInfo(video);
            list.add(video);
        }
        int result = videoMapper.batchInsert(list, now);
        if (result <= 0) {
            failureMsg.insert(0, "很抱歉，导入失败！");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + dataHandles.size() + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 异步转码视频为 9:16（新增/修改后调用，不阻塞接口）
     */
    private void asyncTranscodeVideo(Video video) {
        if (video == null || video.getId() == null) {
            return;
        }
        String url = video.getVideoUrl();
        if (StringUtils.isEmpty(url)) {
            return;
        }
        threadPoolTaskExecutor.execute(() -> transcodeAndUpdate(video.getId(), url));
    }

    /**
     * 批量异步转码所有未处理视频（补录历史数据）
     */
    @Override
    public void refreshVideoSize() {
        threadPoolTaskExecutor.execute(() -> {
            List<Video> nullList = videoMapper.selectVideoSizeNull();
            if (nullList == null) {
                return;
            }
            for (Video v : nullList) {
                if (StringUtils.isEmpty(v.getVideoUrl())) {
                    continue;
                }
                transcodeAndUpdate(v.getId(), v.getVideoUrl());
            }
        });
    }

    /**
     * 批量异步回填系列名与集数（补录历史数据）
     */
    @Override
    public void refreshVideoSeries() {
        threadPoolTaskExecutor.execute(() -> {
            List<Video> list = videoMapper.selectVideoList(new Video());
            if (list == null) {
                return;
            }
            for (Video v : list) {
                fillSeriesInfo(v);
                videoMapper.updateVideoSeriesName(v.getId(), v.getSeriesName(), v.getEpisodeNum());
            }
        });
    }

    /**
     * 依据名称末尾数字解析系列名与集数
     */
    private void fillSeriesInfo(Video video) {
        String name = video.getName();
        if (StringUtils.isEmpty(name)) {
            return;
        }
        Matcher matcher = Pattern.compile("(\\d+)$").matcher(name);
        if (matcher.find()) {
            video.setEpisodeNum(Integer.valueOf(matcher.group(1)));
            String seriesName = name.substring(0, matcher.start());
            video.setSeriesName(seriesName.isEmpty() ? name : seriesName);
        } else {
            video.setSeriesName(name);
        }
    }

    /**
     * 下载解析宽高；已 9:16 仅记录宽高，否则模糊填充转码后回传并更新地址
     */
    private void transcodeAndUpdate(Long id, String url) {
        try {
            int[] size = VideoUtil.getVideoSize(url);
            if (size != null && is916(size[0], size[1])) {
                // 已是 9:16，仅记录宽高，不转码
                videoMapper.updateVideoSize(id, size[0], size[1], null);
                return;
            }
            File transcoded = VideoUtil.transcodeTo916(url);
            if (transcoded == null) {
                return;
            }
            try {
                String newUrl = aliService.sliceUploadFile("transcode.mp4", transcoded);
                videoMapper.updateVideoSize(id, VideoUtil.TARGET_WIDTH, VideoUtil.TARGET_HEIGHT, newUrl);
            } finally {
                if (!transcoded.delete()) {
                    transcoded.deleteOnExit();
                }
            }
        } catch (Exception e) {
            log.error("视频转码处理失败: id={}, url={}", id, url, e);
        }
    }

    /** 是否接近 9:16（宽高比在 0.53 ~ 0.60 之间视为 9:16，无需转码） */
    private boolean is916(int width, int height) {
        if (width <= 0 || height <= 0) {
            return false;
        }
        double ratio = (double) width / height;
        return ratio >= 0.53 && ratio <= 0.60;
    }
}
