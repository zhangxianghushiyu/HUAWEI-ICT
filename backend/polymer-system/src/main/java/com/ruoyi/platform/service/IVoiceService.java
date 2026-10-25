package com.ruoyi.platform.service;

import com.ruoyi.platform.domain.Voice;

import java.util.List;

/**
 * 语音信息Service接口
 * 
 * @author ruoyi
 * @date 2025-02-20
 */
public interface IVoiceService 
{
    /**
     * 查询语音信息
     * 
     * @param id 语音信息主键
     * @return 语音信息
     */
    public Voice selectVoiceById(Long id);

    /**
     * 查询语音信息列表
     * 
     * @param voice 语音信息
     * @return 语音信息集合
     */
    public List<Voice> selectVoiceList(Voice voice);

    /**
     * 新增语音信息
     * 
     * @param voice 语音信息
     * @return 结果
     */
    public int insertVoice(Voice voice);

    /**
     * 修改语音信息
     * 
     * @param voice 语音信息
     * @return 结果
     */
    public int updateVoice(Voice voice);

    /**
     * 批量删除语音信息
     * 
     * @param ids 需要删除的语音信息主键集合
     * @return 结果
     */
    public int deleteVoiceByIds(Long[] ids);

    /**
     * 删除语音信息信息
     * 
     * @param id 语音信息主键
     * @return 结果
     */
    public int deleteVoiceById(Long id);
}
