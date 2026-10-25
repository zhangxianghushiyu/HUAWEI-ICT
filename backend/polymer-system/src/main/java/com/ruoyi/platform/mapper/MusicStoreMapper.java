package com.ruoyi.platform.mapper;

import com.ruoyi.platform.domain.MusicStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 曲库信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-02-07
 */
public interface MusicStoreMapper 
{
    /**
     * 查询曲库信息
     * 
     * @param id 曲库信息主键
     * @return 曲库信息
     */
    public MusicStore selectMusicStoreById(Long id);

    /**
     * 查询曲库信息列表
     * 
     * @param musicStore 曲库信息
     * @return 曲库信息集合
     */
    public List<MusicStore> selectMusicStoreList(MusicStore musicStore);

    /**
     * 新增曲库信息
     * 
     * @param musicStore 曲库信息
     * @return 结果
     */
    public int insertMusicStore(MusicStore musicStore);

    /**
     * 修改曲库信息
     * 
     * @param musicStore 曲库信息
     * @return 结果
     */
    public int updateMusicStore(MusicStore musicStore);

    /**
     * 删除曲库信息
     * 
     * @param id 曲库信息主键
     * @return 结果
     */
    public int deleteMusicStoreById(Long id);

    /**
     * 批量删除曲库信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMusicStoreByIds(Long[] ids);


    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int batchInsert(@Param("list") List<MusicStore> list, @Param("createTime") String createTime);
}
