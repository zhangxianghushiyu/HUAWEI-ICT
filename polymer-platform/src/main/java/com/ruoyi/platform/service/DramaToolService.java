package com.ruoyi.platform.service;

public interface DramaToolService {


    /**
     * 数据转移
     */
    void dataTransfer(Integer classifyId, String dramaIdStr);

    /**
     * 修改地址
     */
    void modifyUrl(String oldUrl, String newUrl);
}
