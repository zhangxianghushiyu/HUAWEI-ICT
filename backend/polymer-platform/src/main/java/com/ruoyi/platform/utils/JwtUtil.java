package com.ruoyi.platform.utils;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.ruoyi.platform.constant.Constant;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2023/7/28 17:03
 */
@Component
public class JwtUtil {

    //秘钥
    final static String key = "q1bc23cadsfy12gs345fhg6sdaa234advcjkn78hggdfg9";


    /**
     * 签发JWT
     */
    public static String createJWT(String id) {
        Map<String, Object> map = new HashMap<String, Object>() {
            {
                long time = System.currentTimeMillis();
                put("id", Integer.parseInt(id));
                put("expire_time", time + 1000L * 60 * 60 * 24 * Constant.USER_LOGIN_TOKEN_EXPIRE_TIME);
                put("create_time", time);
            }
        };
        return JWTUtil.createToken(map, key.getBytes());
    }


    /**
     * 验证JWT
     */
    public static boolean verifyJWT(String token) {
        try {
            return JWTUtil.verify(token, key.getBytes());
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 获取JWT
     */
    public static JWT getJWT(String token) {
        return JWTUtil.parseToken(token);
    }

    /**
     * 获取JWT中传入的id
     */
    public static Integer getJWTId(String token) {
        return Integer.parseInt(getJWT(token).getPayload("id").toString());
    }

    /**
     * 通过request获取用户ID
     */
    public static Integer getRequestUserId(HttpServletRequest request) {
        try {
            return Integer.parseInt(getJWT(request.getHeader("token")).getPayload("id").toString());
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 获取JWT生成时间
     */
    public static String getJWTCreateTime(String token) {
        JWT jwt = getJWT(token);
        Object createTime = jwt.getPayload("create_time");
        long l = Long.parseLong(createTime.toString());
        // 转换为LocalDateTime对象
        LocalDateTime localDateTime = LocalDateTimeUtil.of(l);
        return LocalDateTimeUtil.format(localDateTime, DatePattern.NORM_DATETIME_PATTERN);
    }


    /**
     * 获取JWT过期时间
     */
    public static String getJWTExpireTime(String token) {
        JWT jwt = getJWT(token);
        Object expireTime = jwt.getPayload("expire_time");
        long l = Long.parseLong(expireTime.toString());
        // 转换为LocalDateTime对象
        LocalDateTime localDateTime = LocalDateTimeUtil.of(l);
        return LocalDateTimeUtil.format(localDateTime, DatePattern.NORM_DATETIME_PATTERN);
    }
}
