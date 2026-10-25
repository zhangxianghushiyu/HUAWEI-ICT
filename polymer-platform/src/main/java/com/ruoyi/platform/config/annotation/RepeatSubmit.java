package com.ruoyi.platform.config.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解防止表单重复提交
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit {

    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    long interval() default 3000;

    /**
     * 提示消息
     */
    String message() default "请勿重复提交，请稍候再试";

}
