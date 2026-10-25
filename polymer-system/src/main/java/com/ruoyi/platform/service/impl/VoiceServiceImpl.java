package com.ruoyi.platform.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.platform.domain.Voice;
import com.ruoyi.platform.mapper.VoiceMapper;
import com.ruoyi.platform.service.IVoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 语音信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-02-20
 */
@Service
public class VoiceServiceImpl implements IVoiceService 
{
    @Autowired
    private VoiceMapper voiceMapper;

    /**
     * 查询语音信息
     * 
     * @param id 语音信息主键
     * @return 语音信息
     */
    @Override
    public Voice selectVoiceById(Long id)
    {
        return voiceMapper.selectVoiceById(id);
    }

    /**
     * 查询语音信息列表
     * 
     * @param voice 语音信息
     * @return 语音信息
     */
    @Override
    public List<Voice> selectVoiceList(Voice voice)
    {
        return voiceMapper.selectVoiceList(voice);
    }

    /**
     * 新增语音信息
     * 
     * @param voice 语音信息
     * @return 结果
     */
    @Override
    public int insertVoice(Voice voice)
    {
        voice.setCreateTime(DateUtils.getNowDate());
        return voiceMapper.insertVoice(voice);
    }

    /**
     * 修改语音信息
     * 
     * @param voice 语音信息
     * @return 结果
     */
    @Override
    public int updateVoice(Voice voice)
    {
        voice.setUpdateTime(DateUtils.getNowDate());
        return voiceMapper.updateVoice(voice);
    }

    /**
     * 批量删除语音信息
     * 
     * @param ids 需要删除的语音信息主键
     * @return 结果
     */
    @Override
    public int deleteVoiceByIds(Long[] ids)
    {
        return voiceMapper.deleteVoiceByIds(ids);
    }

    /**
     * 删除语音信息信息
     * 
     * @param id 语音信息主键
     * @return 结果
     */
    @Override
    public int deleteVoiceById(Long id)
    {
        return voiceMapper.deleteVoiceById(id);
    }
}
