package com.ruoyi.platform.dto;

import com.ruoyi.platform.domain.Video;

import java.io.Serializable;
import java.util.List;

/**
 * 视频批量上传请求对象
 *
 * @author ruoyi
 */
public class VideoUploadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 分类ID */
    private Integer classifyId;

    /** 系列名 */
    private String seriesName;

    /** 视频列表（name + videoUrl） */
    private List<Video> videos;

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
