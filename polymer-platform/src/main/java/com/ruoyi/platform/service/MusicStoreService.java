package com.ruoyi.platform.service;

import com.ruoyi.platform.dto.MusicAnswerDTO;
import com.ruoyi.platform.vo.MusicAnswerVO;
import com.ruoyi.platform.vo.MusicStoreVO;

public interface MusicStoreService {


    /**
     * 获得歌曲信息
     */
    MusicStoreVO get();

    /**
     * 答题
     */
    MusicAnswerVO answer(MusicAnswerDTO dto);


}
