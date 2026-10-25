package com.ruoyi.common.utils;


import com.ruoyi.common.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 加密工具封装
 */
public class EncryptUtils {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtils.class);


    /**
     * 用户密码加密
     *
     * @param res 需要加密的身份证号
     * @return
     */
    public static String passwordEncryption(String res,String salt) {
        String result = "";
        try {
            result = EncryptUtil.aesEncryptString(res, salt);
        } catch (Exception e) {
            logger.info("异常信息", e);
        }
        return result;
    }


    /**
     * 用户密码解密
     *
     * @param res 需要解密的密文
     * @return
     */
    public static String passwordDecrypt(String res,String salt) {
        String result = "";
        try {
            result = EncryptUtil.aesDecryptString(res, salt);
        } catch (Exception e) {
            logger.info("异常信息", e);
        }
        return result;
    }


    public static void main(String[] args) {
//        String s = passwordEncryption("18888888888");
//        logger.info("加密后:" + s);
//        String s1 = passwordDecrypt(s);
//        logger.info("解密后:" + s1);
    }
}
