package com.ruoyi.platform.mapper;

import com.ruoyi.platform.vo.VideoCategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoCategoryDao {

    /**
     * 查询视频分类列表
     */
    List<VideoCategoryVO> selectVideoCategoryVO();
}
