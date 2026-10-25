package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.MusicStore;
import com.ruoyi.platform.vo.MusicStoreVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MusicStoreDao {
    int deleteByPrimaryKey(Integer id);

    int insert(MusicStore record);

    int insertSelective(MusicStore record);

    MusicStore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MusicStore record);

    int updateByPrimaryKey(MusicStore record);

    /**
     * 随机获取一首歌
     */
    MusicStoreVO selectRandMusic();
}