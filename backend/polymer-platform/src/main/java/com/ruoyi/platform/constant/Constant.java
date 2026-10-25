
package com.ruoyi.platform.constant;


import java.math.BigDecimal;

/**
 * 常量
 */
public class Constant {

    /**
     * 用户登录token
     */
    public static final String USER_LOGIN_TOKEN = "user:login:token:";

    /**
     * 防重复提交
     */
    public static final String USER_REPEAT_SUBMIT = "user:repeatSubmit:";

    /**
     * 用户登录token过期时间（天）；
     */
    public static final Long USER_LOGIN_TOKEN_EXPIRE_TIME = 30L;

    /**
     * 鉴权
     */
    public static final String TOKEN = "token";

    /**
     * 发送短信验证码
     */
    public static final String SEND_CODE = "send_code:";

    /**
     * 发送短信验证码时间控制
     */
    public static final String SEND_CODE_TIME = "send_code_time:";

    /**
     * redis监听测试
     */
    public static final String REDIS_LISTENER_TEST = "test:123:456:";


    /**
     * 任务完成信息加入缓存
     */
    public static final String TASK = "task:";

    /**
     * 任务是否开启
     */
    public static final String TASK_STATUS = "task_status:";

    /**
     * redis默认值1
     */
    public static final Integer REDIS_DEFAULT_VALUE = 1;


    /**
     * 兑换比例
     */
    public static final String CONVERT_RATIO = "convert_ratio";

    /**
     * redis保存时间(10天)
     */
    public static final Long REDIS_TIME_TEN_DAY = 10L;

    /**
     * redis保存官方二维码
     */
    public static final String OFFICIAL_CODE = "official_code";


    /**
     * 比例计算方式
     */
    public static final BigDecimal PROPORTIONAL_CALCULATION_METHOD = new BigDecimal("0.01");

    /**
     * 默认关卡1
     */
    public static final Integer DEFAULT_BARRIER_1 = 1;

    /**
     * 默认关卡2
     */
    public static final Integer DEFAULT_BARRIER_2 = 2;


    /**
     * 请求返回状态码
     */
    public enum ResultCode {

        ERROR(500, "error"),
        SUCCESS(200, "success");

        private int code;
        private String msg;

        ResultCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

    }

}
