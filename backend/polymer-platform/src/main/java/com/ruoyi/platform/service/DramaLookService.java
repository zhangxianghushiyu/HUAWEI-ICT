package com.ruoyi.platform.service;

import com.ruoyi.platform.vo.MyDramaLookVO;

import java.util.List;

public interface DramaLookService {


    /**
     * 查看某一个短剧
     *
     * @param dramaDetailsId
     * @return
     */
    void look(Integer dramaDetailsId);


    /**
     * 查询浏览记录
     * @return
     */
    List<MyDramaLookVO> getDramaLookRecordListVO();

    /**
     * 删除浏览记录
     * @return
     */
    void remove(Integer id);
}
