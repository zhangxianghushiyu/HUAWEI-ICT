package com.ruoyi.platform.utils;

import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckUtils {

    private static final Logger logger = LoggerFactory.getLogger(CheckUtils.class);


    /**
     * 验证手机号
     *
     * @param mobile
     * @return
     */
    public static Boolean checkMobile(String mobile) {
        Boolean flag = false;
        try {
            String telRegex = "[1][3456789]\\d{9}";
            // "[1]"代表第1位为数字1，"[3   578]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            if (TextUtils.isEmpty(mobile)) {
                return false;
            } else
                flag = mobile.matches(telRegex);
        } catch (Exception e) {
            logger.info("手机号验证异常");
        }
        return flag;
    }

    public static void main(String[] args) {
        logger.info(checkMobile("11852024523")+"");
    }
}
