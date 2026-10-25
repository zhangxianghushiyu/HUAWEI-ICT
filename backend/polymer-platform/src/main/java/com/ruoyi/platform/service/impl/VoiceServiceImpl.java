package com.ruoyi.platform.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.platform.domain.Voice;
import com.ruoyi.platform.mapper.VoiceDao;
import com.ruoyi.platform.service.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VoiceServiceImpl implements VoiceService {

    @Autowired
    private VoiceDao voiceDao;

    @Override
    public String getVoiceAddress(Integer type) {
        Voice voice = voiceDao.selectVoiceByType(type);
        if (Objects.nonNull(voice) && StrUtil.isNotEmpty(voice.getUrl())) {
            return voice.getUrl();
        }
        return null;
    }
}
