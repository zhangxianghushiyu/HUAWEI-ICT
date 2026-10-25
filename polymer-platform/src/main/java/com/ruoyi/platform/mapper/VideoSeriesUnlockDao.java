package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.VideoSeriesUnlock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoSeriesUnlockDao {

    /**
     * 查看视频系列解锁配置
     */
    VideoSeriesUnlock selectVideoSeriesUnlock();
}
