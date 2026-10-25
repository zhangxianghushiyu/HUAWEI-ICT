package com.ruoyi.platform.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @date 2022/2/16 14:38
 */
public interface UploadService {

    /**
     * 上传
     *
     * @return
     */
    String uploadPicture(MultipartFile file) throws Exception;



    /**
     * 删除
     *
     * @param url
     */
    void remove(String url);


}
