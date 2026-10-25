package com.ruoyi.platform.service.impl;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.platform.domain.MusicStore;
import com.ruoyi.platform.mapper.MusicStoreMapper;
import com.ruoyi.platform.service.IMusicStoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 曲库信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-07
 */
@Service
public class MusicStoreServiceImpl implements IMusicStoreService 
{
    @Autowired
    private MusicStoreMapper musicStoreMapper;

    /**
     * 查询曲库信息
     * 
     * @param id 曲库信息主键
     * @return 曲库信息
     */
    @Override
    public MusicStore selectMusicStoreById(Long id)
    {
        return musicStoreMapper.selectMusicStoreById(id);
    }

    /**
     * 查询曲库信息列表
     * 
     * @param musicStore 曲库信息
     * @return 曲库信息
     */
    @Override
    public List<MusicStore> selectMusicStoreList(MusicStore musicStore)
    {
        return musicStoreMapper.selectMusicStoreList(musicStore);
    }

    /**
     * 新增曲库信息
     * 
     * @param musicStore 曲库信息
     * @return 结果
     */
    @Override
    public int insertMusicStore(MusicStore musicStore)
    {
        musicStore.setCreateTime(DateUtils.getNowDate());
        return musicStoreMapper.insertMusicStore(musicStore);
    }

    /**
     * 修改曲库信息
     * 
     * @param musicStore 曲库信息
     * @return 结果
     */
    @Override
    public int updateMusicStore(MusicStore musicStore)
    {
        musicStore.setUpdateTime(DateUtils.getNowDate());
        return musicStoreMapper.updateMusicStore(musicStore);
    }

    /**
     * 批量删除曲库信息
     * 
     * @param ids 需要删除的曲库信息主键
     * @return 结果
     */
    @Override
    public int deleteMusicStoreByIds(Long[] ids)
    {
        return musicStoreMapper.deleteMusicStoreByIds(ids);
    }

    /**
     * 删除曲库信息信息
     * 
     * @param id 曲库信息主键
     * @return 结果
     */
    @Override
    public int deleteMusicStoreById(Long id)
    {
        return musicStoreMapper.deleteMusicStoreById(id);
    }

    @Override
    public String importDataHandle(List<MusicStore> dataHandles, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(dataHandles) || dataHandles.isEmpty()) {
            throw new ServiceException("导入数据不能为空！");
        }
        if (dataHandles.size() >= 10000) {
            throw new ServiceException("模版数据导入上限！");
        }
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        List<MusicStore> list = new ArrayList<>();
        String now = DateUtil.now();
        for (MusicStore dataHandle : dataHandles) {
            MusicStore musicStore = new MusicStore();
            BeanUtils.copyProperties(dataHandle, musicStore);
            list.add(musicStore);
        }
        int result = musicStoreMapper.batchInsert(list, now);
        if (result <= 0) {
            failureMsg.insert(0, "很抱歉，导入失败！");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + dataHandles.size() + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
