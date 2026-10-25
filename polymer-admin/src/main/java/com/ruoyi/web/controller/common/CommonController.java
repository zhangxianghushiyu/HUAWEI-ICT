package com.ruoyi.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.ruoyi.platform.service.AliService;
import com.ruoyi.platform.vo.AliStsTokenVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@RestController
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private AliService aliService;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        String objectName;
        String today = DateUtil.today();
        String path = today + "/";
        //获得最后一个.后面的后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //uuid和后缀进行拼接
        objectName = IdUtil.fastSimpleUUID() + suffix;
        AjaxResult ajax = AjaxResult.success();
        ajax.put("fileName", objectName);
        objectName = path + objectName;
        String string = aliService.sliceUploadFile(objectName, file);
        ajax.put("url", string);
        log.info("上传文件：{}", string);
        return ajax;
//        long size = file.getSize();
//        double m = size / (1024.0 * 1024.0);
//        log.info("文件大小：" + m + " M");
//        return AjaxResult.success();
//        try
//        {
//            // 上传文件路径
//            String filePath = RuoYiConfig.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);
////            String url = serverConfig.getUrl() + fileName;
//            String url = "http://1.94.12.209:8080" + fileName;
//            AjaxResult ajax = AjaxResult.success();
//            ajax.put("fileName", fileName);
//            ajax.put("url", url);
//            return ajax;
//        }
//        catch (Exception e)
//        {
//            return AjaxResult.error(e.getMessage());
//        }
    }


    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 获取阿里stsToken
     */
    @GetMapping("/common/getStsToken")
    public AjaxResult getStsToken() throws Exception {
        AliStsTokenVO aliStsTokenVO = aliService.getAliStsTokenVO();
        return AjaxResult.success(aliStsTokenVO);
    }


    /**
     * 通用上传请求
     */
    @PostMapping("/common/uploadLocal")
    public AjaxResult uploadLocal(MultipartFile file, String type) throws Exception {
        //type1表示阿里支付配置
        long size = file.getSize();
        double m = size / (1024.0 * 1024.0);
        log.info("文件大小：" + m + " M");
        try {
            String filePath = null;
            if ("aliPayConfig".equals(type)) {
                // 上传文件路径（支付宝）
                filePath = "/mnt/ali/pay";
            } else if ("wxPayConfig".equals(type)) {
                //上传文件路径（微信）
                filePath = "/mnt/wx/pay";
            } else {
                return AjaxResult.error("上传失败");
            }
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.uploadNoProfile(filePath, file);
            String url = filePath + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            log.info("fileName:{}", fileName);
            log.info("url:{}", url);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

}
