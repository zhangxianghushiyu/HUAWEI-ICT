package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.DramaDetailsListVO;

import java.util.List;

public interface DramaDetailsService {




    /**
     * 查询短剧详细信息集合
     */
    List<DramaDetailsListVO> getDramaDetailsListVO(Integer dramaId);

}
