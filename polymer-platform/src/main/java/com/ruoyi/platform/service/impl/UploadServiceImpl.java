package com.ruoyi.platform.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.ruoyi.platform.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @date 2022/2/16 14:47
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    // 服务器上访问路径
    @Value("${file.profile}")
    private String profile;
    // 服务器外网地址
    @Value("${service.ip}")
    private String serviceIp;
    //端口号
    @Value("${server.port}")
    private String port;

    @Override
    public String uploadPicture(MultipartFile file) throws Exception {
        long kb = file.getSize();
        double mb = kb * 1.0 / 1024;
        String mbStr = String.format("%.2f", mb);
        mb = Double.parseDouble(mbStr);
        //文件大小
//        if (mb > 5) {
//            throw new ApiException("文件不可超过5M");
//        }
        FileOutputStream out = null;
        try {
            String today = DateUtil.today();
            // 文件名
            String ossFileName = file.getOriginalFilename();
            //获取后缀
            String substring = ossFileName.substring(ossFileName.indexOf("."));
            StringBuilder fileUrl = new StringBuilder();
            fileUrl.append(System.currentTimeMillis()).append(RandomUtil.randomNumbers(10));
            fileUrl.append(substring);
            byte[] bytes = file.getBytes();
            String path = profile + "/" + today;
            File newFile = new File(path);
            if (!newFile.exists()) {
                boolean mkdirs = newFile.mkdirs();
                if (!mkdirs) {
                    log.error("创建文件夹失败");
                    return null;
                }
            }
            String path2 = profile + "/" + today + "/" + fileUrl;
            File newFile2 = new File(path2);
            out = new FileOutputStream(newFile2);
            out.write(bytes);
            out.flush();
            String ip = serviceIp;
            String url = "http://" + ip + ":" + port + "/file/" + today + "/" + fileUrl;
            log.info("上传文件：{}", url);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public void remove(String url) {
        try {
            //截取传入的地址
            String substring = url.substring(url.lastIndexOf("file/") + 5);
            String s = profile + "/" + substring;
            File f = new File(s);
            f.delete();
            log.info("删除的文件：" + url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
