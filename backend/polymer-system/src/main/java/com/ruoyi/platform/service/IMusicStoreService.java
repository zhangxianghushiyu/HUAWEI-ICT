package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.MusicStore;

import java.util.List;

/**
 * 曲库信息Service接口
 * 
 * @author ruoyi
 * @date 2025-02-07
 */
public interface IMusicStoreService 
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
     * 批量删除曲库信息
     * 
     * @param ids 需要删除的曲库信息主键集合
     * @return 结果
     */
    public int deleteMusicStoreByIds(Long[] ids);

    /**
     * 删除曲库信息信息
     * 
     * @param id 曲库信息主键
     * @return 结果
     */
    public int deleteMusicStoreById(Long id);


    /**
     * 导入数据
     *
     * @param dataHandles        数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importDataHandle(List<MusicStore> dataHandles, Boolean isUpdateSupport,
                                   String operName);
}
