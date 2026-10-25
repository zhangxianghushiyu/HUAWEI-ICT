package com.ruoyi.platform.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;

public class GsonUtil {
    private static volatile Gson gson;

    /**
     * 获取配置好的Gson实例
     * @return Gson实例
     */
    public static Gson getGson() {
        if (gson == null) {
            synchronized (GsonUtil.class) {
                if (gson == null) {
                    gson = new GsonBuilder()
                            // 设置日期格式
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            // 禁止转义html标签
                            .disableHtmlEscaping()
                            // 格式化输出
                            .setPrettyPrinting()
                            // 允许空值
                            .serializeNulls()
                            // 其他自定义配置...
                            .create();
                }
            }
        }
        return gson;
    }

    /**
     * 将对象转换为JSON字符串
     */
    public static String toJson(Object object) {
        return getGson().toJson(object);
    }

    /**
     * 将JSON字符串转换为对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return getGson().fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSON字符串转换为对象（针对泛型类型）
     */
    public static <T> T fromJson(String json, Type type) {
        try {
            return getGson().fromJson(json, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将JSON字符串转换为对象（针对TypeToken类型）
     */
    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return fromJson(json, typeToken.getType());
    }
}